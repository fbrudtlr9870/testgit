package com.kh.spring.member.model.dao;

import java.util.Map;

import com.kh.spring.member.model.vo.Member;

public interface MemberDAO {

	int memberEnrollEnd(Member m);

	int loginCheck(Map<String, String> map);

	Member selectOne(String userId);

	int updateMember(Member m);

	int checkIdDuplicate(String userId);




}
