package site.re_fill.auth.application;

import org.springframework.transaction.annotation.Transactional;
import site.re_fill.auth.dto.request.OAuthToken;
import site.re_fill.auth.dto.response.JwToken;

public interface LoginService {
    @Transactional
    JwToken login(OAuthToken request);
}
