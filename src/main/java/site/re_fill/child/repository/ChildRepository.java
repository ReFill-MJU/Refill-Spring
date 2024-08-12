package site.re_fill.child.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.re_fill.child.domain.Child;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findAllByMemberId(final Long memberId);
}
