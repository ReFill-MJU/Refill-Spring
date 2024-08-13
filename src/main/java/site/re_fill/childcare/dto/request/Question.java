package site.re_fill.childcare.dto.request;

import jakarta.persistence.Lob;
import lombok.Builder;

@Builder
public record Question(
        @Lob
        String data
) {
    public static Question of(final String answer1, final String answer2, final String answer3) {
        return Question.builder()
                .data("성격:" + answer1
                        + "알레르기 및 질환:" + answer2
                        + "좋아하는 것과 싫어하는 것:" + answer3
                )
                .build();
    }
}
