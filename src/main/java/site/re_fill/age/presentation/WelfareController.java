package site.re_fill.age.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import site.re_fill.age.application.WelfareService;
import site.re_fill.age.dto.response.GetBusinessList;
import site.re_fill.auth.dto.AuthDto;

@Tag(name = "복지 API", description = "복지 관련 API")
@RestController
@RequiredArgsConstructor
public class WelfareController {

    private final WelfareService welfareService;

    @Operation(
            summary = "지원 사업 리스트 조회",
            description = "입력받은 나이에 맞는 지원 사업 리스트를 조회합니다."
    )
    @GetMapping("/welfare/{age}/business")
    public ResponseEntity<GetBusinessList> getList(
            @AuthenticationPrincipal AuthDto auth,
            @PathVariable("age") Integer age
    ) {
        return ResponseEntity.ok(welfareService.getBusinessList(age));
    }

}
