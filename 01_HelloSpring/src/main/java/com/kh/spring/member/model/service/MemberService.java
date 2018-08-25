package com.kh.spring.member.model.service;

import java.util.Map;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	public static final int LOGIN_OK = 1;
	public static final int WRONG_PASSWORD = 0;
	public static final int ID_NOT_EXIST = -1;

	int memberEnrollEnd(Member m);

	int loginCheck(Map<String, String> map);

	Member selectOne(String userId);

	int updateMember(Member m);

	int checkIdDuplicate(String userId);

	





}
