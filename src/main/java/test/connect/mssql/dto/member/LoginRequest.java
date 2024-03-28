package test.connect.mssql.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
