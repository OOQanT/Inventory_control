package test.connect.mssql.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.connect.mssql.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> notJoinedUserTryLogin(UsernameNotFoundException ex){
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                .body(ApiResponse.createError(ex.getMessage()));
    }
}
