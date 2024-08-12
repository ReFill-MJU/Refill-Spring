package site.re_fill.welfare.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.re_fill.welfare.dto.response.GetWelfareList;
import site.re_fill.welfare.dto.response.WelfareDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WelfareServiceImpl implements WelfareService{

    private final WelfareModuleService welfareModuleService;

    @Override
    public GetWelfareList getWelfareList(final int age) {
        List<WelfareDto> welfareList = welfareModuleService.findAllByAge(age).stream()
                .map(WelfareDto::of).toList();
        return GetWelfareList.of(welfareList);
    }
}
