package com.kh.spring.demo.model.vo;

import java.util.Arrays;

/*컨테이너가 쓸 객체만 @붙여주면됨*/
public class Dev {
	private int devNo;
	private String devName;
	private String devEmail;
	private int devAge;
	private String[] devLang;
	
	public Dev() {
		super();
	}
	
	public Dev(int devNo, String devName, String devEmail, int devAge, String[] devLang) {
		super();
		this.devNo = devNo;
		this.devName = devName;
		this.devEmail = devEmail;
		this.devAge = devAge;
		this.devLang = devLang;
	}

	public String getDevEmail() {
		return devEmail;
	}

	public void setDevEmail(String devEmail) {
		this.devEmail = devEmail;
	}

	public int getDevNo() {
		return devNo;
	}
	public void setDevNo(int devNo) {
		this.devNo = devNo;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public int getDevAge() {
		return devAge;
	}
	public void setDevAge(int devAge) {
		this.devAge = devAge;
	}
	public String[] getDevLang() {
		return devLang;
	}
	public void setDevLang(String[] devLang) {
		this.devLang = devLang;
	}

	@Override
	public String toString() {
		return "Dev [devNo=" + devNo + ", devName=" + devName + ", devEmail=" + devEmail + ", devAge=" + devAge
				+ ", devLang=" + Arrays.toString(devLang) + "]";
	}
	
	
}
