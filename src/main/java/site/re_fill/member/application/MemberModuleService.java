package site.re_fill.member.application;

import site.re_fill.member.domain.Member;

import java.util.Optional;

public interface MemberModuleService {
    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberBySocialId(String socialId);

    Optional<Member> findMemberByRefreshToken(String refreshToken);

    Member save(Member member);
}
