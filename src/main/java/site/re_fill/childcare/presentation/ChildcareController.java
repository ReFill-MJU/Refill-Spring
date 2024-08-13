package site.re_fill.childcare.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.re_fill.auth.dto.AuthDto;
import site.re_fill.childcare.application.ChildcareService;
import site.re_fill.childcare.dto.request.Question;
import site.re_fill.childcare.dto.response.Answer;
import site.re_fill.childcare.dto.response.SummaryAnswer;

@Tag(name = "보육 API", description = "보육 관련 API")
@RestController
@RequiredArgsConstructor
public class ChildcareController {

    private final ChildcareService childcareService;

    @Operation(
            summary = "보육 GPT 질문",
            description = "보육 정보를 학습한 GPT를 통해 질문합니다."
    )
    @PostMapping("/childcare/{childId}/test")
    public ResponseEntity<Answer> interpretQuestion(
            @AuthenticationPrincipal final AuthDto auth,
            @PathVariable("childId") Long childId,
            @RequestBody final Question question
    ) {
        return ResponseEntity.ok(childcareService.interpretQuestion(childId, question));
    }

    @Operation(
            summary = "아이 성향 요약 GPT",
            description = "아이의 성향을 GPT를 통해 질문합니다."
    )
    @PostMapping("/childcare/{childId}/summary")
    public ResponseEntity<SummaryAnswer> interpretPersonality(
            @AuthenticationPrincipal final AuthDto auth,
            @PathVariable("childId") Long childId
    ) {
        return ResponseEntity.ok(childcareService.interpretPersonality(childId));
    }
}
