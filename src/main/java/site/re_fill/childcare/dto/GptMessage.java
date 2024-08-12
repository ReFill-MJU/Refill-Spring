package site.re_fill.childcare.dto;

import lombok.Builder;

@Builder
public record GptMessage(
        String role,
        String content
) {
    public static GptMessage of(final String role, final String content) {
        return GptMessage.builder()
                .role(role)
                .content(content)
                .build();
    }
}
