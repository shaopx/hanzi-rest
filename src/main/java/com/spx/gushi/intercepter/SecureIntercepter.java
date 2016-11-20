package com.spx.gushi.intercepter;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/11/20.
 */
public class SecureIntercepter extends HandlerInterceptorAdapter {
    static Logger logger = Logger.getLogger("SecureIntercepter");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("getLocalAddr:" + request.getLocalAddr());
        logger.info("sec:" + request.getParameter("sec"));
        logger.info("path:" + request.getPathInfo());
        logger.info("ParameterMap:" + request.getParameterMap());


        return true;
    }


}
