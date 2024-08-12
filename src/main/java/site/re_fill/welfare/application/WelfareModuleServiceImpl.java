package site.re_fill.welfare.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.re_fill.welfare.domain.Welfare;
import site.re_fill.welfare.repository.WelfareRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WelfareModuleServiceImpl implements WelfareModuleService {

    private final WelfareRepository welfareRepository;

    @Override
    public List<Welfare> findAllByAge(final int age) {
        return welfareRepository.findAllByAge(age);
    }
}
