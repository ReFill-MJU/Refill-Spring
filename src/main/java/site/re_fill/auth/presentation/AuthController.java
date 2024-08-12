package site.re_fill.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.re_fill.auth.application.LoginService;
import site.re_fill.auth.dto.request.OAuthToken;
import site.re_fill.auth.dto.response.JwToken;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<JwToken> callBack(
            @RequestBody final OAuthToken request
    ) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
