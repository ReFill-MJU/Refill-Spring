package site.re_fill.age.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.re_fill.age.domain.Business;
import site.re_fill.age.dto.response.GetBusinessList;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WelfareServiceImpl implements WelfareService {

    private final WelfareModuleService welfareModuleService;

    @Override
    public GetBusinessList getBusinessList(final int age) {
        List<String> commentList = welfareModuleService.findBusinessByValue(age).stream()
                .map(Business::getValue).toList();
        return GetBusinessList.of(commentList);
    }
}
