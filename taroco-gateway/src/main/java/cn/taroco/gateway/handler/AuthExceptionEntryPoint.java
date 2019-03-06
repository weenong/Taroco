package cn.taroco.gateway.handler;

import cn.taroco.common.constants.CommonConstant;
import cn.taroco.common.exception.DefaultError;
import cn.taroco.common.web.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(CommonConstant.UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            map.put("error","invalid_token");
            response.getWriter().write(objectMapper.writeValueAsString(map));
//            if(cause instanceof InvalidTokenException) {
//
//            }else{
//                response.getWriter().write("{'error':'没有token'}");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}