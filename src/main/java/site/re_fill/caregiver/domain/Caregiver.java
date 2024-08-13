package site.re_fill.caregiver.domain;

import jakarta.persistence.*;
import lombok.*;
import site.re_fill.common.domain.Gender;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Caregiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String area;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String imageUrl;

    private Double rate;

    private Long costPerHour;

    @Enumerated(EnumType.STRING)
    private PreferPeriod preferPeriod;

    private Boolean isAuthentication;

    private Boolean isPublicFigure;

    private String reviewSummary;

    @Builder
    private Caregiver(final String name, final Integer age, final String area, final Gender gender, final String imageUrl, final Double rate, final Long costPerHour, final PreferPeriod preferPeriod, final Boolean isAuthentication, final Boolean isPublicFigure, final String reviewSummary) {
        this.name = name;
        this.age = age;
        this.area = area;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.rate = rate;
        this.costPerHour = costPerHour;
        this.preferPeriod = preferPeriod;
        this.isAuthentication = isAuthentication;
        this.isPublicFigure = isPublicFigure;
        this.reviewSummary = reviewSummary;
    }
}
