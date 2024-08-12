package site.re_fill.welfare.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetWelfareList(
        List<WelfareDto> welfareList
) {
    public static GetWelfareList of(List<WelfareDto> welfareList) {
        return GetWelfareList.builder()
                .welfareList(welfareList)
                .build();
    }
}
