package site.re_fill.childcare.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.re_fill.auth.dto.AuthDto;
import site.re_fill.childcare.application.ChildcareService;
import site.re_fill.childcare.dto.request.Question;
import site.re_fill.childcare.dto.response.Answer;

@RestController
@RequiredArgsConstructor
public class ChildcareController {

    private final ChildcareService childcareService;

    @GetMapping("/childcare/test")
    public ResponseEntity<Answer> interpretQuestion(
            @AuthenticationPrincipal final AuthDto auth,
            @RequestBody final Question question
    ) {
        return ResponseEntity.ok(childcareService.interpretQuestion(question));
    }
}
