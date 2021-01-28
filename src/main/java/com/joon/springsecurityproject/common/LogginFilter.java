package com.joon.springsecurityproject.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LogginFilter  extends GenericFilterBean {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start(((HttpServletRequest)servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse); //안하면 안넘어감
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
    }
}
