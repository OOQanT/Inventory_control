package test.connect.mssql.controller.refresh;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import test.connect.mssql.dto.ApiResponse;
import test.connect.mssql.entity.Refresh;
import test.connect.mssql.jwt.JWTUtil;
import test.connect.mssql.service.refresh.RefreshService;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshService refreshService;
    private final JWTUtil jwtUtil;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse> reissue(HttpServletRequest request, HttpServletResponse response){

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
            }
        }

        if(refresh == null){
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(ApiResponse.createError("인증키가 존재하지 않습니다."));
        }

        try{
            jwtUtil.isExpired(refresh);
        }catch (ExpiredJwtException e) {

            //response status code
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createError("인증이 만료되었습니다. 다시 로그인해주세요"));
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createError("잘못된 토큰입니다."));
        }

        Boolean isExist = refreshService.existsByRefresh(refresh);
        if (!isExist) {
            //response body
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createError("서버에서 확인할 수 없는 토큰입니다."));
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshService.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(ApiResponse.createSuccessWithNoContent());
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = new Refresh(username,refresh,date.toString());

        refreshService.newRrefresh(refreshEntity);
    }
}
