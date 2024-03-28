package test.connect.mssql.controller.member;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.connect.mssql.dto.ApiResponse;
import test.connect.mssql.dto.member.LoginRequest;

@Slf4j
@RestController
public class LoginController {

/*    @PostMapping("/loginProc")
    public ResponseEntity<ApiResponse> login(@Validated @RequestBody LoginRequest loginRequest,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createFail(bindingResult));
        }
    }*/
}
