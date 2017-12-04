package com.horacio.Aspect;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horacio.Enum.ResultEnum;
import com.horacio.Exception.LabsException;
import com.horacio.Response.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Aspect
@Component
public class Verify {

    private final static Logger logger = LoggerFactory.getLogger(Verify.class);

    /**
     * 任何操作之前要进行用户的session验证
     */
    @Before("execution(public * com.horacio.Controller.*.*(..))")
    public void userVerify() throws Exception{

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        url
        String url = request.getRequestURL().toString();
        //若url是登录接口，则不需要进行用户验证
        if(url.contains("/login")||url.contains("/logout")){
//            HttpSession session =request.getSession();
//            logger.info(" before session_id={}",session.getId());
        }else{
            HttpSession session =request.getSession();
//            logger.info("session={}",session);
//            logger.info("session_id={}",session.getAttribute("username"));
            if(session.getAttribute("userid")==null){
                throw new LabsException(ResultEnum.USER_VERFIY_ERROR.getCode(),ResultEnum.USER_VERFIY_ERROR.getMsg());
            }
        }
    }

    /**
     * 当用户登录成功之后，保存该用户的Session信息
     * @throws Exception
     */
    @AfterReturning(returning = "object",value = "execution(public * com.horacio.Controller.AdminController.login(..))")
    public void setSession(JoinPoint joinPoint,Object object) throws Exception{
        if(object instanceof Result){
            Result result = (Result) object;
            //登录成功
            if(result.getErrCode() == 0){
                //获取request请求数据
//                Map<String,Object> map = (Map<String,Object>) joinPoint.getArgs()[0];
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                HttpSession session =request.getSession();
                session.setMaxInactiveInterval(259200);
                ObjectNode data = (ObjectNode) result.getData();
                session.setAttribute("userid",data.get("userid").toString());
                session.setAttribute("superuser",data.get("superuser").toString());
            }
        }
    }

}
