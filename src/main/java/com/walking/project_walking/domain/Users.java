package com.walking.project_walking.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "phone", nullable = false, length = 16)
    private String phone;

    @Column(name = "nickname", nullable = false, length = 256)
    private String nickname;

    @Column(name = "gender", nullable = false) // TINYINT를 해주려면 Boolean으로 해줘야 함.
    private Boolean gender;

    @Column(name = "birth", nullable = false)
    @Temporal(TemporalType.DATE) // Date 타입 포맷을 맞춰준다고 함. (yyyy-MM-dd 형태)
    private LocalDate birth;

    @CreatedDate // 계정 생성 날짜
    @Column(name = "join_Date", nullable = false)
    private LocalDate joinDate; // date 형식이기 때문에 LocalDate(yyyy-MM-dd)로 변경

    @LastModifiedDate // 마지막 접속한 날짜, 로그아웃 시 업데이트 되도록 설정할 것.
    @Column(name = "last_login")
    private LocalDateTime lastLogin; // TIMESTAMP 형식이기 때문에 LocalDateTime으로 둠.

    @Builder.Default
    @Column(name = "login_count")
    private Long loginCount = 0L;

    @Column(name = "login_browser", length = 512)
    private String loginBrowser;

    @Builder.Default
    @Column(name = "user_level")
    private Integer userLevel = 0;

    @Builder.Default
    @Column(name = "user_exp")
    private Long userExp = 0L;

    @Builder.Default
    @Column(name = "point")
    private Integer point = 0;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Builder.Default
    @Column(name = "profile_image", length = 256) // url 형태로 받아와도 되는 형태 (VARCHAR 이므로)
    private String profileImage = "https://walkingproject.s3.ap-northeast-2.amazonaws.com/41166136-8ormi.jpg";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Column(name = "title_id")
    private Integer titleId;

    // 팔로우 관련 필드
    @OneToMany(mappedBy = "followingUser")
    private List<Follow> followers; // 현재 사용자의 id

    @OneToMany(mappedBy = "followUser")
    private List<Follow> followings; // 현재 사용자가 팔로잉 (-> 이후 상대 사용자의 팔로워)

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getIsActive();
    }

    public Users(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Users(
            String email,
            String password,
            String name,
            String phone,
            String nickname,
            Boolean gender,
            LocalDate birth,
            LocalDate joinDate,
            LocalDateTime lastLogin,
            Long loginCount,
            String loginBrowser,
            Integer userLevel,
            Long userExp,
            Integer point,
            Boolean isActive,
            String profileImage
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
        this.loginCount = loginCount;
        this.loginBrowser = loginBrowser;
        this.userLevel = userLevel;
        this.userExp = userExp;
        this.point = point;
        this.isActive = isActive;
        this.profileImage = profileImage;
    }

    public void deactivate() {
        this.isActive = false;
    }
}