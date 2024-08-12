package site.re_fill.welfare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.re_fill.welfare.domain.Welfare;

import java.util.List;

public interface WelfareRepository extends JpaRepository<Welfare, Long> {

    List<Welfare> findAllByAge(final int age);
}
