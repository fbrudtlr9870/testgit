package com.kh.spring.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes("memberLoggedIn")
@Controller
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@RequestMapping(value="/member/memberEnroll.do")
	public String memberEnroll() {
		if(logger.isDebugEnabled())
			logger.debug("회원등록페이지");
		return "member/memberEnroll";
	}
	
	@RequestMapping(value="/member/memberEnrollEnd.do",method=RequestMethod.POST)
	public String memberEnrollEnd(Member m) {	
		if(logger.isDebugEnabled())
			logger.debug("회원등록처리페이지");
		logger.debug(m.toString());
		System.out.println("member@controller="+m);
		String rawPassword = m.getPassword();
		System.out.println("암호화 전 : "+rawPassword);
		/*password 암호화시작*/
		//salt값을 사용하지 않은 버젼
		//String encodedPassword = shaPasswordEncoder.encodePassword(rawPassword, null);
		
		//salt값을 사용한 버젼
		//String encodedPassword=shaPasswordEncoder.encodePassword(rawPassword, m.getUserId()+rawPassword);
		
		//BCryptPasswordEncoder사용 버젼
		String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
		//$2a$10$LTJrsYqAwdcZJYSBskMNKOC1GlSpcdZs4t5MDPfTDar8yevUZbCVy
		//$2a$10$ : 알고리즘 및 알고리즘 옵션
		//LTJrsYqAwdcZJYSBskMNKO:random salt 값(22자리)
		//C1GlSpcdZs4t5MDPfTDar8yevUZbCVy : 암호화된 패스워드 (31자리)
		
		m.setPassword(encodedPassword);
		
		/*password 암호화끝*/
		System.out.println("암호화 후"+m.getPassword());
		int result = memberService.memberEnrollEnd(m);
		System.out.println("result"+result);
		return "redirect:/";
	}
	
/*	@RequestMapping("/member/memberLogin.do")
	public String memberLogin(@RequestParam String userId
							 ,@RequestParam String password
							 ,Model model) {
		String encodedPassword = bcryptPasswordEncoder.encode(password);
		
		Map<String,String> map=null;
		map.put("userId", userId);
		map.put("password",encodedPassword);
		int result = memberService.loginCheck(map);
		
		String msg="";
		String loc="";
			
		//String dbPassword = memberService.selectOne(userId);
		Member m = memberService.selectOne(userId);
		System.out.println(m);
		
		String msg="";
		String loc="/";
		
		if(m==null) {
			msg="존재하지않는 아이디";
		}else {
			if(bcryptPasswordEncoder.matches(password, m.getPassword())) {
				msg="로그인성공";
				model.addAttribute("memberLoggedIn",m);
			}else {
				msg="비밀번호 틀림";
			}
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		return "common/msg";
	}*/
	@RequestMapping("/member/memberLogin.do")
	public ModelAndView memberLogin(@RequestParam String userId
							 ,@RequestParam String password
							 ,Model model) {
		if(logger.isDebugEnabled())
			logger.debug("로그인요청");
		
		//리턴할 ModelAndView객체 생성
		ModelAndView mav = new ModelAndView();
		
		
		Member m = memberService.selectOne(userId);
		System.out.println(m);
		
		String msg="";
		String loc="/";
		
		if(m==null) {
			msg="존재하지않는 아이디";
		}else {
			if(bcryptPasswordEncoder.matches(password, m.getPassword())) {
				msg="로그인성공";
				mav.addObject("memberLoggedIn",m);
			}else {
				msg="비밀번호 틀림";
			}
		}
		
		mav.addObject("msg",msg);
		mav.addObject("loc",loc);
		//view단 지정
		mav.setViewName("common/msg");
		
		return mav;
	}
	
	@RequestMapping("/member/memberLogout.do")
	public String memberLogout(SessionStatus sessionStatus) {
		if(logger.isDebugEnabled())
			logger.debug("로그아웃요청");
		//현재 session상태를 끝났다고 마킹함.
		if(!sessionStatus.isComplete())
			sessionStatus.setComplete();
		
		return "redirect:/";
	}
	
	@RequestMapping("/member/memberView.do")
	public ModelAndView memberList(@RequestParam("userId") String userId) {
		if(logger.isDebugEnabled())
			logger.debug("회원정보보기요청");
		logger.debug(userId);
		System.out.println("userId="+userId);
		
		ModelAndView mav = new ModelAndView();
		
		Member m = memberService.selectOne(userId);
		System.out.println("member@memberView="+m);
		
		mav.addObject("member",m);
		System.out.println("이거되는거야??");
		//view단 지정
		//view단 지정안해주면 RequestMapping의 값에서 확장자를 떼고 viewName으로 가져감.
		mav.setViewName("member/memberView");
		
		return mav;
	}
	
	@RequestMapping(value="/member/memberUpdate.do")
	public ModelAndView memberUpdate(Member member){
		if(logger.isDebugEnabled())
			logger.debug("회원정보수정 처리페이지");
		
		ModelAndView mav = new ModelAndView();
		System.out.println(member);
			
		//1.비지니스로직 실행
		int result = memberService.updateMember(member);
		
		//2.처리결과에 따라 view단 분기처리
		String loc = "/"; 
		String msg = "";
		if(result>0){ 
			msg="회원정보수정성공!";
			mav.addObject("memberLoggedIn", member);
		}
		else msg="회원정보수정실패!";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		mav.setViewName("common/msg");
		
		return mav;
	}
	
	
	/******************** Spring Ajax 시작 
	 * @throws JsonProcessingException 
	 * @throws IOException *********************/
	/*
	 * 1. Steam을 이용한 ajax처리
	 */
//	@RequestMapping("/member/checkIdDuplicate.do")
//	public void checkIdDuplicate(@RequestParam String userId,HttpServletResponse response) throws IOException {//@RequestParam써주면 필수
//		logger.debug("stream ajax : "+userId);
//		int count = memberService.checkIdDuplicate(userId);
//		boolean isUsable = count==0?true:false;
//		response.getWriter().print(isUsable);
//	}
	
//	@RequestMapping("/member/checkIdDuplicate.do")
//	public ModelAndView checkIdDuplicate(@RequestParam String userId) throws IOException {//@RequestParam써주면 필수
//		logger.debug("jsonView ajax : "+userId);
//		ModelAndView mav = new ModelAndView();
//		Map<String,Object> map = new HashMap<>();
//		//업무로직
//		int count = memberService.checkIdDuplicate(userId);
//		boolean isUsable = count==0?true:false;
//		map.put("isUsable", isUsable);
//		
//		mav.addAllObjects(map);
//		//(중요) jsonView빈으로 연결됨.
//		mav.setViewName("jsonView");
//		
//		return mav;
//	}
	
	/*
	 * 3. @ResponseBody를 이용해 jsonString 리턴하기
	 */
//	@RequestMapping("/member/checkIdDuplicate.do")
//	@ResponseBody
//	public String checkIdDuplicate(@RequestParam("userId") String userId) throws JsonProcessingException {//@RequestParam써주면 필수
//		logger.debug("@Response-jsonString ajax : "+userId);
//		
//		Map<String,Object> map = new HashMap<>();
//		//jackson라이브러리에서 사용하는 바인더
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonStr = null;
//		
//		//업무로직
//		int count = memberService.checkIdDuplicate(userId);
//		boolean isUsable = count==0?true:false;
//		
//		//jsonString 변환
//		map.put("isUsable", isUsable);
//		jsonStr = mapper.writeValueAsString(map);
//		
//		return jsonStr;
//	}
	
	/*
	 * 4. @ResponseBody를 이용해 일반자바객체 리턴하기
	 */
	@RequestMapping("/member/checkIdDuplicate.do")
	@ResponseBody
	public Map<String,Object> checkIdDuplicate(@RequestParam("userId") String userId){//@RequestParam써주면 필수
		logger.debug("@Response-javaobj ajax : "+userId);
		
		Map<String,Object> map = new HashMap<>();
		
		//업무로직
		int count = memberService.checkIdDuplicate(userId);
		boolean isUsable = count==0?true:false;
		
		map.put("isUsable", isUsable);
		
		return map;
	}
}
