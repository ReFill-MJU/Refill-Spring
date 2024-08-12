package site.re_fill.child.dto;

import lombok.Builder;
import site.re_fill.child.domain.Child;
import site.re_fill.child.domain.Gender;

@Builder
public record ChildDto(
        Long id,
        String name,
        String age,
        Gender gender
) {
    public static ChildDto of(final Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .name(child.getName())
                .age(child.getAge())
                .gender(child.getGender())
                .build();
    }
}
