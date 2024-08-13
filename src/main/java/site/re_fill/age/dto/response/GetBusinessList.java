package site.re_fill.age.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetBusinessList(
        List<String> businessList
) {
    public static GetBusinessList of(List<String> businessList) {
        return GetBusinessList.builder()
                .businessList(businessList)
                .build();
    }
}
