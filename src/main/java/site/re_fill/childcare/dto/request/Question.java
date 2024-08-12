package site.re_fill.childcare.dto.request;

import jakarta.persistence.Lob;

public record Question(
        @Lob
        String data
) {
}
