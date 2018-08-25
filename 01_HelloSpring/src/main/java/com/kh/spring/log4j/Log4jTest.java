package com.kh.spring.log4j;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

public class Log4jTest {
	//org.apache.log4j.Logger 객체생성
	//private Logger logger = Logger.getLogger(Log4jTest.class);//Log4jTest.class -> 카테고리명
	//org.slf4j.Logger객체 생성
	//Simple Logging Facade 4 Java
	private Logger logger = LoggerFactory.getLogger(Log4jTest.class); //interface로 구현하게 되면 나중에 구현객체가 바뀌어도 수정하지 않아도됨.
	
	public static void main(String[] args) {
		//new Log4jTest().test();
		new Log4jTest().test2();
	}

	private void test2() {	
		//logger.fatal("Fatal 로그");
		logger.error("Error 로그");
		logger.warn("Warn 로그");
		logger.info("Info 로그");
		logger.debug("Debug 로그");
		logger.trace("Trace 로그");
	}

	/*private void test() {
		//TRACE < DEBUG < INFO < WARN < ERROR < FATAL
		logger.setLevel(Level.ERROR);
		//logger.setPriority(Level.DEBUG); setLevel이랑 같은 용도
		
		logger.fatal("Fatal 로그");
		logger.error("Error 로그");
		logger.warn("Warn 로그");
		logger.info("Info 로그");
		logger.debug("Debug 로그");
		logger.trace("Trace 로그");
		
	}*/

}
