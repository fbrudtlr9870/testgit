package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

public interface BoardService {

	List<Map<String, String>> selectEmpList(int cPage, int numperPage);

	int totalCount();

	int insertBoard(Board board, List<Attachment> attachList);

	Board selectBoardView(int boardNo);

	List<Map<String, String>> selectAttachView(int boardNo);

}
