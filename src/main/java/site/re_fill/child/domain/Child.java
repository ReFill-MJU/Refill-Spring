package site.re_fill.child.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.re_fill.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // Mapping
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Child(final String name, final String age, final Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
