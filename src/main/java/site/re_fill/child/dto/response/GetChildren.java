package site.re_fill.child.dto.response;

import lombok.Builder;
import site.re_fill.child.domain.Child;

import java.util.List;

@Builder
public record GetChildren(
        List<String> children
) {
    public static GetChildren of(List<Child> children) {
        return GetChildren.builder()
                .children(children.stream()
                        .map(Child::getName)
                        .toList())
                .build();
    }
}
