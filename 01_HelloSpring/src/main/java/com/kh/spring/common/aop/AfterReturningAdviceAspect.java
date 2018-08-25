package com.kh.spring.common.aop;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.vo.Member;

@Component
@Aspect
public class AfterReturningAdviceAspect {
	Logger logger = LoggerFactory.getLogger(AfterReturningAdviceAspect.class);
	
	@Pointcut("execution(* com.kh.spring.member..*Controller.*Login(..))")
	public void pointcut(){}
	
	@AfterReturning(pointcut="pointcut()", returning="returnObj")
	public void advice(JoinPoint joinPoint, Object returnObj)  {
		//타겟메소드의 리턴 데이터를 다른 용도로 처리할 때 사용함.
		logger.debug("리턴값  = ["+returnObj+"]");//ModelAndView: reference to view with name 'common/msg'; model is {memberLoggedIn=Member [userId=test3, password=$2a$10$73Y8NPMncJ5SMB6YqtpU2eRPH2I6d/W.Qqy4SM.osF8TmjWxrH3ae, userName=비와이, gender=M, age=32, email=df@mailc.com, phone=01012341234, address=null, hobby=[등산, 독서, 게임], enrollDate=2018-05-12], loc=/, msg=로그인 성공!}
		
		ModelAndView mav = (ModelAndView)returnObj;
		Map<String,Object> map = mav.getModel();
		if(map.containsKey("memberLoggedIn")){
			Member m = (Member)map.get("memberLoggedIn");
			logger.info("["+m.getUserId()+"]이 로그인 함.");
		}
		
	}
	
}
