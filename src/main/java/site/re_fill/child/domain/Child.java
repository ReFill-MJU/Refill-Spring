package site.re_fill.child.domain;

import jakarta.persistence.*;
import lombok.*;
import site.re_fill.common.domain.Gender;
import site.re_fill.member.domain.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String birth;

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
    private Child(final String name, final String birth, final Integer age, final Gender gender) {
        this.name = name;
        this.birth = birth;
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

    public void updateAge(final Integer age) {
        this.age = age;
    }

    public String getKoreanAge() {
        return switch (this.age + 1) {
            case 1 -> "한살";
            case 2 -> "두살";
            case 3 -> "세살";
            case 4 -> "네살";
            case 5 -> "다섯살";
            case 6 -> "여섯살";
            default -> "유치원 갈 나이";
        };
    }

    public Long getDBirth() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
            Date date = formatter.parse(this.birth);
            Date currentDate = new Date();
            long differenceInMillis = currentDate.getTime() - date.getTime();
            return TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
