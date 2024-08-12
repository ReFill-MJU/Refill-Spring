package site.re_fill.child.dto.request;

import site.re_fill.child.domain.Child;
import site.re_fill.child.domain.Gender;

public record CreateChild(
    String name,
    String age,
    Gender gender
) {
    public Child toEntity() {
        return Child.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .build();
    }
}
