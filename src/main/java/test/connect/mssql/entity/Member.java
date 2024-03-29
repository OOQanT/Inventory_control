package test.connect.mssql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.connect.mssql.dto.member.JoinDto;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private String role;

    private boolean mailAuthenticated;

    public Member(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = "USER";
        this.mailAuthenticated = false;
    }

    public Member(JoinDto joinDto) {
        this.username = joinDto.getUsername();
        this.email = joinDto.getEmail();
        this.phoneNumber = joinDto.getStartNumber() + "-" + joinDto.getMiddleNumber() + "-" + joinDto.getLastNumber();
    }

    public Member(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public void changeRole(String role){
        this.role = role;
    }

    public void changeMailAuthenticate(boolean mailAuthenticated){
        this.mailAuthenticated = mailAuthenticated;
    }

    public void changePassword(String password){
        this.password = password;
    }
}
