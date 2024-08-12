package site.re_fill.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.re_fill.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialId(final String socialId);

    Optional<Member> findByRefreshToken(final String refreshToken);
}
