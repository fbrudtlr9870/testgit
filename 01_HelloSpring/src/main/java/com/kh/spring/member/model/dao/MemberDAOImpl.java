package com.kh.spring.member.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int memberEnrollEnd(Member m) {
		return sqlSession.insert("member.memberEnroll",m);
	}

	@Override
	public int loginCheck(Map<String, String> map) {
		return sqlSession.selectOne("member.loginCheck",map);
	}

	@Override
	public Member selectOne(String userId) {
		return sqlSession.selectOne("member.selectOne",userId);
	}

	@Override
	public int updateMember(Member m) {
		return sqlSession.update("member.updateMember",m);
	}

	@Override
	public int checkIdDuplicate(String userId) {
		return sqlSession.selectOne("member.checkIdDuplicate",userId);
	}



}
