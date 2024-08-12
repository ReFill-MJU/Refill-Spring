package site.re_fill.welfare.dto.response;

import lombok.Builder;
import site.re_fill.welfare.domain.Welfare;

@Builder
public record WelfareDto(
        Long id,
        String backup,
        Integer age
) {
    public static WelfareDto of(final Welfare welfare) {
        return WelfareDto.builder()
                .id(welfare.getId())
                .backup(welfare.getBackup())
                .age(welfare.getAge())
                .build();
    }

}
