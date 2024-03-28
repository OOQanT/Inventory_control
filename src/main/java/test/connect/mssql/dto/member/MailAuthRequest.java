package test.connect.mssql.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailAuthRequest {

    private String email;

    @NotBlank(message = "인증번호를 입력해주세요.")
    private String ran;

    public MailAuthRequest() {
    }

    public MailAuthRequest(String email, String ran) {
        this.email = email;
        this.ran = ran;
    }
}
