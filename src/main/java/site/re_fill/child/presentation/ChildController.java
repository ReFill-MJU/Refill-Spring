package site.re_fill.child.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.re_fill.auth.dto.AuthDto;
import site.re_fill.child.application.ChildService;
import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.response.GetChildren;

@Tag(name = "아이 API", description = "아이 관련 API")
@RestController
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @Operation(
            summary = "아이 추가",
            description = "관리할 아이의 정보를 입력받아 추가합니다."
    )
    @PostMapping("/child")
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal AuthDto auth,
            @RequestBody CreateChild createChild
    ) {
        childService.createChild(auth.id(), createChild);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "내 아이 리스트 조회",
            description = "관리할 아이의 리스트를 받아옵니다."
    )
    @GetMapping("/child")
    public ResponseEntity<GetChildren> getChildren(
            @AuthenticationPrincipal AuthDto auth
    ) {
        return ResponseEntity.ok(childService.getChildren(auth.id()));
    }


}
