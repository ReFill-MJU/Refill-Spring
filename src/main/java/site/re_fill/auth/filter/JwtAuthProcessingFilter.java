package site.re_fill.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.re_fill.auth.application.JwtService;
import site.re_fill.auth.dto.AuthDto;
import site.re_fill.member.application.MemberModuleService;
import site.re_fill.member.domain.Member;
import site.re_fill.member.domain.RoleType;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberModuleService memberModuleService;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberModuleService.findMemberByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getId()),
                            reIssuedRefreshToken);
                });
    }

    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberModuleService.save(member);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .flatMap(accessToken -> jwtService.extractId(accessToken)
                        .flatMap(memberModuleService::findMemberById))
                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(Member myMember) {
        log.info("JwtAuthenticationProcessingFilter saveAuthentication 호출 확인");
        AuthDto authInfoDTO = AuthDto.builder()
                .id(myMember.getId())
                .username(myMember.getName())
                .authorities(
                        Set.of(myMember.getRoleType()).stream()
                                .map(RoleType::getKey)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toUnmodifiableSet()))
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(authInfoDTO, null,
                        authoritiesMapper.mapAuthorities(authInfoDTO.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
