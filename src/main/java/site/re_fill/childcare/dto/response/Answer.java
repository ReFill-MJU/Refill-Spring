package site.re_fill.childcare.dto.response;

import jakarta.persistence.Lob;
import lombok.Builder;

@Builder
public record Answer(
        @Lob
        String data
) {
    public static Answer of(final String data) {
        return Answer.builder()
                .data(data)
                .build();
    }
}
