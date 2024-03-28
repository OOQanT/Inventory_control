package test.connect.mssql.dto.member;

import lombok.Getter;
import lombok.Setter;
import test.connect.mssql.entity.Member;

@Getter
@Setter
public class JoinResponse {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;

    public JoinResponse() {
    }

    public JoinResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
    }

}
