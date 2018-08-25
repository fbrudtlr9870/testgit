package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * 1. preHandle : DispatcherServlet => controller
 * 2. postHandle : controller => DispatcherServlet 업무로직 끝나고 dispatcher로 return될때
 * 3. afterCompletion : view단 처리가 끝난 후. 응답객체가 완성된 후
 */

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String loc="";
		String msg="";
		
		if(request.getSession().getAttribute("memberLoggedIn")==null){
			msg="로그인이나 하고 들어와라";
			loc="";
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}else {
			msg="ㅇㅇ";
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("====== start ======");
			logger.debug(request.getRequestURI());
			logger.debug("-----------------------");
		}
		
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("--------- view --------");
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("======= end =======");
		}
		super.afterCompletion(request, response, handler, ex);
	}

}
