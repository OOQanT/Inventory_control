package test.connect.mssql.controller.member;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.connect.mssql.dto.ApiResponse;
import test.connect.mssql.dto.member.JoinDto;
import test.connect.mssql.dto.member.JoinResponse;
import test.connect.mssql.dto.member.MailAuthRequest;
import test.connect.mssql.entity.Member;
import test.connect.mssql.service.mail.MailService;
import test.connect.mssql.service.member.MemberService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/joinProc")
    public ResponseEntity<ApiResponse> joinProcess(@Validated @RequestBody JoinDto joinDto, BindingResult bindingResult)  {
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createFail(bindingResult));
        }

        if(!joinDto.checkPassword()){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createError("재입력한 비밀번호가 일치하지 않습니다."));
        }

        Member member = memberService.joinMember(joinDto);
        JoinResponse joinResponse = new JoinResponse(member);


        String ran = mailService.sendHTMLEmail(member.getEmail());
        log.info("ran = {}", ran);

        return ResponseEntity.status(HttpServletResponse.SC_CREATED)
                .body(ApiResponse.createSuccess(joinResponse));
    }

    @PostMapping("/send_JoinMail")
    public ResponseEntity<ApiResponse> join_authentication(@Validated @RequestBody MailAuthRequest mailAuthRequest,
                                                           BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createFail(bindingResult));
        }

        String email = mailAuthRequest.getEmail();
        String ran = mailAuthRequest.getRan();

        log.info("사용자 입력 = {}", email);

        try{
            boolean authed = mailService.mail_authentication(ran, email);
            if(authed){
                memberService.authed(email);

                return ResponseEntity.status(HttpServletResponse.SC_OK)
                        .body(ApiResponse.createError("인증이 완료되었습니다."));
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(ApiResponse.createError("인증번호가 일치하지 않습니다."));
            }
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createError(e.getMessage()));
        }
    }
}
