package site.re_fill.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String socialId;

    private String name;

    private String imageUrl;

    private String refreshToken;

    private RoleType roleType;

    @Builder
    public Member(final String socialId, final String name, final String imageUrl, final String refreshToken, final RoleType roleType) {
        this.socialId = socialId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.refreshToken = refreshToken;
        this.roleType = roleType;
    }

    public void updateRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
