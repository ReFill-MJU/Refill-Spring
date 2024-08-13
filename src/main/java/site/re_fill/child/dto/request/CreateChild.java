package site.re_fill.child.dto.request;

import site.re_fill.child.domain.Child;
import site.re_fill.common.domain.Gender;

public record CreateChild(
        String name,
        String birth,
        Gender gender
) {
    public Child toEntity() {
        return Child.builder()
                .name(name)
                .birth(birth)
                .gender(gender)
                .build();
    }
}
