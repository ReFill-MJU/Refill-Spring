package site.re_fill.child.domain;

import jakarta.persistence.*;
import lombok.*;
import site.re_fill.common.domain.Gender;
import site.re_fill.member.domain.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String answer1;

    private String answer2;

    private String answer3;

    // Mapping
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Child(final String name, final Integer age, final Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public void updateAnswer(final String answer, final Integer number) {
        switch (number) {
            case 1:
                this.answer1 = answer;
            case 2:
                this.answer2 = answer;
            case 3:
                this.answer3 = answer;
        }
    }
}
