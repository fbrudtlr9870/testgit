package com.kh.spring.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public int memberEnrollEnd(Member m) {
		return memberDAO.memberEnrollEnd(m);
	}

	@Override
	public int loginCheck(Map<String, String> map) {
		return memberDAO.loginCheck(map);
	}

	@Override
	public Member selectOne(String userId) {
		return memberDAO.selectOne(userId);
	}

	@Override
	public int updateMember(Member m) {
		return memberDAO.updateMember(m);
	}

	@Override
	public int checkIdDuplicate(String userId) {
		return memberDAO.checkIdDuplicate(userId);
	}




}
