package site.re_fill.child.dto.response;

import lombok.Builder;
import site.re_fill.child.dto.ChildDto;

@Builder
public record GetChild(
        ChildDto child
) {
    public static GetChild of(final ChildDto child) {
        return GetChild.builder()
                .child(child)
                .build();
    }
}
