package org.ne.springboot.interceptor;


import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.ne.springboot.utils.ThreadLocalUtils;
import org.ne.springboot.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        System.out.println("token:" + token);

        Map<String, Object> claims = TokenUtils.parseToken(token);
        ThreadLocalUtils.set(claims);

//        去掉前端返回的token前后的双引号
//        token = token.substring(1, token.length() - 1);
        boolean result = TokenUtils.verify(token);
        if (result) {
            boolean adminResult = TokenUtils.adminVerify(token);
            if (adminResult) {
                System.out.println("管理员认证成功");
                return true;
            }

            System.out.println("通过拦截器");
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json = new JSONObject();
            json.put("msg", "token verify fail");
            json.put("code", "401");
            response.getWriter().append(json.toJSONString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            System.out.println("认证失败，未通过拦截器");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
            response.setStatus(500);
            return false;
        }
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.remove();
    }

}
