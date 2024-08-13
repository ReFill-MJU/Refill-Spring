package site.re_fill.child.dto.response;

import lombok.Builder;
import site.re_fill.child.domain.Child;
import site.re_fill.child.dto.SimpleChildDto;

import java.util.List;

@Builder
public record GetChildren(
        List<SimpleChildDto> children
) {
    public static GetChildren of(final List<Child> children) {
        return GetChildren.builder()
                .children(children.stream()
                        .map(SimpleChildDto::of)
                        .toList())
                .build();
    }
}
