package site.re_fill.age.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.re_fill.age.domain.Age;
import site.re_fill.age.domain.Business;
import site.re_fill.age.repository.AgeRepository;
import site.re_fill.age.repository.BusinessRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WelfareModuleServiceImpl implements WelfareModuleService {

    private final AgeRepository ageRepository;
    private final BusinessRepository businessRepository;

    @Override
    public Age findAgeByValue(final int age) {
        return ageRepository.findByValue(age)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Business> findBusinessByValue(final int age) {
        return businessRepository.findAllByAge_Value(age);
    }
}
