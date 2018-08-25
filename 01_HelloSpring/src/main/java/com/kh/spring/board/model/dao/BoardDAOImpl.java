package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Map<String, String>> selectEmpList(int cPage, int numperPage) {
		RowBounds rowBounds = new RowBounds((cPage-1)*numperPage,numperPage);	
		return sqlSession.selectList("board.boardList", null, rowBounds);
	}

	@Override
	public int totalCount() {
		return sqlSession.selectOne("board.totalCount");
	}

	@Override
	public int insertBoard(Board board) {
		return sqlSession.insert("board.insertBoard",board);
	}

	@Override
	public int insertAttachment(Attachment a) {
		return sqlSession.insert("board.insertAttachment",a);
	}

	@Override
	public Board selectBoardView(int boardNo) {
		return sqlSession.selectOne("board.selectBoardView",boardNo);
	}

	@Override
	public List<Map<String, String>> selectAttachView(int boardNo) {
		return sqlSession.selectList("board.selectAttachView",boardNo);
	}
	
}
