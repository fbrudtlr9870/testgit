package com.kh.spring.demo.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.demo.model.service.DemoService;
import com.kh.spring.demo.model.vo.Dev;

/**
 * @Component 의 자식어노테이션인 @Controller로 등록
 * component-scan에 의해서 빈으로 등록됨.
 * 빈이름은 demoController가 됨.
 *
 */

@Controller
public class DemoController {
	//DI Dependency Injection
	//스프링컨테이너가 DemoService타입의 빈을 자동으로 주입해줌.
	@Autowired
	private DemoService demoService;
	
	//RequestMapping의 주소를 보고 메소드를 호출함. 메소드의 이름은 유일하면 됨.
	@RequestMapping("/demo/demo.do")
	public String demo() {
		System.out.println("/demo/demo.do가 호출됨");
		//viewResolver가 servletContext주소와 .jsp를 붙여줌.
		return "demo/demo";
	}
	
	/*
	 * Controller클래스의 메소드가 가질수있는 파라키터타입
	 * HttpServletRequest
	 * HttpServletResponse
	 * HttpSession
	 * java.util.locale : 요청에 대한 locale
	 * InputStream/Reader : 요청에 대한 입력
	 * OutputStream/Writer : 응답에 대한 출력
	 * @PathValrible
	 * @RequestParam
	 * @RequestHeader
	 * @CookieValue
	 * RequestBody
	 * Map,Model,ModelMap : 뷰에 전달할 모델데이터설정
	 * Command객체 : http요청 파라키터를 커맨드객체에 저장한 vo객체
	 * Error, BindingResult : Command객체 저장결과
	 * SessionStatus
	 * */
	
	@RequestMapping("/demo/demo1.do")
	public String demo1(HttpServletRequest request) {
		//파라미터핸들링
		String devName = request.getParameter("devName");
		int devAge = Integer.parseInt(request.getParameter("devAge"));
		String devEmail = request.getParameter("devEmail");
		String[] devLang = request.getParameterValues("devLang");
		
		Dev dev = new Dev(0, devName,devEmail, devAge, devLang);
		System.out.println("dev@demo1="+dev);
		
		request.setAttribute("dev", dev);
		
		return "demo/demoResult";
	}
	
	@RequestMapping("/demo/demo2.do")
	public String demo2(@RequestParam(value="devName") String devName
			,@RequestParam(value="devAge",required=false,defaultValue="20") int devAge
			,@RequestParam(value="devEmail") String devEmail
			,@RequestParam(value="devLang",required=false) String[] devLang
			,Model model) {
		Dev dev = new Dev(0, devName, devEmail, devAge, devLang);
		System.out.println("dev@demo2.do="+dev);
		model.addAttribute("dev", dev);
		return "demo/demoResult";
	}
	
	@RequestMapping("/demo/demo3.do")
	public String demo3(Model model,Dev dev) {
		System.out.println("dev@demo3.do="+dev);
		model.addAttribute("dev",dev);
		return "demo/demoResult";
	}
	
	//post방식의 메소드만 받음
	@RequestMapping(value="/demo/insertDev.do",method=RequestMethod.POST)
	public String insertDev(Dev dev) {
		int result = demoService.insertDev(dev);
		System.out.println("result@insertDev="+result);
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/demo/selectDevList.do")
	public String selectDevList(Model model) {
		List<Map<String,String>> list = demoService.selectDevList();
		System.out.println("list@selectDevList="+list);
		model.addAttribute("list",list);
		return "demo/demoSelectList";
	}	
	
	@RequestMapping("/demo/searchTest.do")
	public ModelAndView searchTest() {
		ModelAndView mav = new ModelAndView();
		String keyword = "서울 영등포구 영등포로12길 7";
		keyword+="cu";
		mav.addObject("keyword",keyword);
		mav.setViewName("demo/purchase");
		return mav;
	}
	
	
}
