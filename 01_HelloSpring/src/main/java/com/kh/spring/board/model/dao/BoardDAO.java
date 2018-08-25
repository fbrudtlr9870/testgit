package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

public interface BoardDAO {

	List<Map<String, String>> selectEmpList(int cPage, int numperPage);

	int totalCount();

	int insertBoard(Board board);

	int insertAttachment(Attachment a);

	Board selectBoardView(int boardNo);

	List<Map<String, String>> selectAttachView(int boardNo);

}
