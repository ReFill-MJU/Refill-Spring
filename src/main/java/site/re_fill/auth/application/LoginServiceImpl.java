package site.re_fill.auth.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import site.re_fill.auth.dto.request.OAuthToken;
import site.re_fill.auth.dto.response.JwToken;
import site.re_fill.member.application.MemberModuleService;
import site.re_fill.member.domain.Member;
import site.re_fill.member.domain.RoleType;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {

    private final MemberModuleService memberModuleService;
    private final JwtService jwtService;

    @Value("${oauth2.user-info-uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public JwToken login(final OAuthToken request) {
        String userInfo = getUserInfo(request.accessToken());
        Member member = processUserInfo(userInfo);

        // JWT 발급
        String accessToken = jwtService.createAccessToken(member.getId());
        String refreshToken = jwtService.createRefreshToken();

        // 리프레시 토큰을 회원 정보에 업데이트
        member.updateRefreshToken(refreshToken);
        memberModuleService.save(member);

        return JwToken.of(accessToken);
    }

    private String getUserInfo(final String accessToken) {
        // 사용자 정보 요청을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // 사용자 정보 요청
        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    private Member processUserInfo(String userInfo) {
        try {
            JsonNode rootNode = objectMapper.readTree(userInfo);
            JsonNode responseNode = rootNode.path("response");

            String socialId = responseNode.path("id").asText();
            String name = responseNode.path("name").asText();
            String profileImage = responseNode.path("profile_image").asText();

            Member member = memberModuleService.findMemberBySocialId(socialId)
                    .orElse(
                            Member.builder()
                                    .socialId(socialId)
                                    .name(name)
                                    .imageUrl(profileImage)
                                    .roleType(RoleType.MEMBER)
                                    .build()
                    );

            return memberModuleService.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process user info");
        }
    }
}
