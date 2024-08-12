package site.re_fill.child.dto.response;

import lombok.Builder;
import site.re_fill.child.dto.ChildDto;

import java.util.List;

@Builder
public record GetChildren(
        List<ChildDto> children
) {
    public static GetChildren of(List<ChildDto> children) {
        return GetChildren.builder()
                .children(children)
                .build();
    }
}
