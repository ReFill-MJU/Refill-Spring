package site.re_fill.child.dto;

import lombok.Builder;
import site.re_fill.child.domain.Child;
import site.re_fill.common.domain.Gender;

@Builder
public record ChildDto(
        Long id,
        String name,
        Integer age,
        Gender gender,
        String comment
) {
    public static ChildDto of(final Child child, final String comment) {
        return ChildDto.builder()
                .id(child.getId())
                .name(child.getName())
                .age(child.getAge())
                .gender(child.getGender())
                .comment(comment)
                .build();
    }
}
