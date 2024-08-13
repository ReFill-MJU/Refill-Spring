package site.re_fill.review.domain;

import jakarta.persistence.*;
import lombok.*;
import site.re_fill.caregiver.domain.Caregiver;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    private String content;

    // Mapping
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    @Builder
    private Review(final Double score, final String content, final Caregiver caregiver) {
        this.score = score;
        this.content = content;
        this.caregiver = caregiver;
    }
}
