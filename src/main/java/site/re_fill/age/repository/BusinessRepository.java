package site.re_fill.age.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.re_fill.age.domain.Business;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findAllByAge_Value(final Integer value);
}
