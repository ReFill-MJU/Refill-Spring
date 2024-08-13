package site.re_fill.age.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.re_fill.age.domain.Age;

import java.util.List;
import java.util.Optional;

public interface AgeRepository extends JpaRepository<Age, Long> {

    Optional<Age> findByValue(final Integer age);

    List<Age> findAllByValue(final int age);
}
