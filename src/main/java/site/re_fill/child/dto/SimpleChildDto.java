package site.re_fill.child.dto;

import lombok.Builder;
import site.re_fill.child.domain.Child;

@Builder
public record SimpleChildDto(
        Long id,
        String name
) {
    public static SimpleChildDto of(final Child child) {
        return SimpleChildDto.builder()
                .id(child.getId())
                .name(child.getName())
                .build();
    }
}
