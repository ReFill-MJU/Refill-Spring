package site.re_fill.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.re_fill.auth.application.LoginService;
import site.re_fill.auth.dto.request.OAuthToken;
import site.re_fill.auth.dto.response.JwToken;

@Tag(name = "인증 API", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @Operation(
            summary = "로그인",
            description = "로그인합니당ㅋㅋ"
    )
    @PostMapping("/login")
    public ResponseEntity<JwToken> callBack(
            @RequestBody final OAuthToken request
    ) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
