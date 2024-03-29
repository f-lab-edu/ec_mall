package security.jwt;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * AuthenticationEntryPoint
 *
 * 인증 과정에서 실패하거나 인증을 위한 헤더정보를 보내지 않은 경우
 * 401(UnAuthorized) 에러가 발생하게 된다.
 *
 * Spring Security에서 인증되지 않은 사용자에 대한 접근 처리는 AuthenticationEntryPoint가 담당하는데,
 * commence 메소드가 실행되어 처리된다.
 */

@Log4j2
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {
        log.error("UnAuthorized -- message : " + e.getMessage()); // 로그를 남기고
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 로그인 페이지로 리다이렉트.
    }
}
