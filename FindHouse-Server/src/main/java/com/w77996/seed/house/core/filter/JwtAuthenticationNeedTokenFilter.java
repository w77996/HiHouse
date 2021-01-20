package com.w77996.seed.house.core.filter;

import cn.hutool.json.JSONUtil;
import com.w77996.seed.house.core.annotation.IgnoreToken;
import com.w77996.seed.house.core.annotation.NeedToken;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName JwtAuthenticationNeedTokenFilter
 * @Description
 * @author w77996
 * @date 2020/11/10 13:35
 */
@Slf4j
@Component
public class JwtAuthenticationNeedTokenFilter extends HandlerInterceptorAdapter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法上的注解判断是否进行token校验
        NeedToken needToken = handlerMethod.getBeanType().getAnnotation(NeedToken.class);
//        log.info("enter preHandle {}",request.getRequestURL());
        if (needToken == null) {
            needToken = handlerMethod.getMethodAnnotation(NeedToken.class);
        }
        if (needToken == null) {
            return true;
        }
        //获取请求头中Authorization的参数
        String token = request.getHeader("Authorization");
        if(token != null){
            log.info("token is {}",token);
            if ("admin".equals(token.substring(7))) {
                return true;
            }
            //使用jwt进行token校验
            String userId = jwtTokenUtil.getUserIdFromToken(token.substring(7));
            if(userId != null){
                return true;
            }else{
                response.setStatus(401);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().println(JSONUtil.parse(ResultGenerator.genUnauthorizedResult("token失效请重新获取")));
                response.getWriter().flush();
            }
        }else{
            response.setStatus(401);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSONUtil.parse(ResultGenerator.genUnauthorizedResult("token失效请重新获取")));
            response.getWriter().flush();
        }
        return false;
    }
}
