package site.re_fill.child.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.re_fill.auth.dto.AuthDto;
import site.re_fill.child.application.ChildService;
import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.request.UpdateAnswer;
import site.re_fill.child.dto.response.GetChild;
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
            @AuthenticationPrincipal final AuthDto auth,
            @RequestBody final CreateChild createChild
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
            @AuthenticationPrincipal final AuthDto auth
    ) {
        return ResponseEntity.ok(childService.getChildren(auth.id()));
    }

    @Operation(
            summary = "내 아이 상세 조회",
            description = "내 아이의 상세정보를 받아옵니다."
    )
    @GetMapping("/child/{childId}")
    public ResponseEntity<GetChild> getChildren(
            @AuthenticationPrincipal final AuthDto auth,
            @PathVariable("childId") final Long childId
    ) {
        return ResponseEntity.ok(childService.getChild(childId));
    }

    @Operation(
            summary = "내 아이 질문 업데이트",
            description = "내 아이의 질문 답변을 등록합니다."
    )
    @PatchMapping("/child/{childId}/answer/{answerNumber}")
    public ResponseEntity<String> updateAnswer(
            @AuthenticationPrincipal final AuthDto auth,
            @PathVariable("childId") final Long childId,
            @PathVariable("answerNumber") final Integer answerNumber,
            @RequestBody final UpdateAnswer updateAnswer
    ) {
        String name = childService.updateAnswer(childId, answerNumber, updateAnswer);
        return ResponseEntity.ok(name);
    }
}
