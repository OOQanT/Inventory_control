package test.connect.mssql.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호를 다시 입력해주세요")
    private String rePassword;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일형식으로 입력해주세요")
    private String email;


    @NotBlank(message = "전화번호 앞자리를 입력해주세요")
    private String startNumber;

    @NotBlank(message = "중간 번호를 입력해주세요")
    private String middleNumber;

    @NotBlank(message = "마지막 번호를 입력해주세요")
    private String lastNumber;

    public JoinDto() {
    }

    public JoinDto(String username, String password, String rePassword, String email, String startNumber, String middleNumber, String lastNumber) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
        this.startNumber = startNumber;
        this.middleNumber = middleNumber;
        this.lastNumber = lastNumber;
    }

    public boolean checkPassword(){
        return password.equals(rePassword);
    }
}
