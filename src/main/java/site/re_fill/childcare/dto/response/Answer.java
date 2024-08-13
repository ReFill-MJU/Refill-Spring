package site.re_fill.childcare.dto.response;

import jakarta.persistence.Lob;
import lombok.Builder;

@Builder
public record Answer(
        @Lob
        String data,
        byte[] mp3Audio
) {
    public static Answer of(final String data, final byte[] mp3Audio) {
        return Answer.builder()
                .data(data)
                .mp3Audio(mp3Audio)
                .build();
    }
}
