package site.re_fill.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.re_fill.member.domain.Member;
import site.re_fill.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberModuleServiceImpl implements MemberModuleService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findMemberById(final Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findMemberBySocialId(final String socialId) {
        return memberRepository.findBySocialId(socialId);
    }

    @Override
    public Optional<Member> findMemberByRefreshToken(final String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public Member save(final Member member) {
        return memberRepository.save(member);
    }

}
