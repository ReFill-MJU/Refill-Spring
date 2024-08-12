package site.re_fill.auth.dto.response;

import lombok.Builder;

@Builder
public record JwToken(
        String accessToken
) {

    public static JwToken of(final String accessToken) {
        return JwToken.builder().accessToken(accessToken).build();
    }
}
