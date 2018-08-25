package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.exception.BoardException;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO boardDAO;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<Map<String, String>> selectEmpList(int cPage, int numperPage) {
		return boardDAO.selectEmpList(cPage,numperPage);
	}

	@Override
	public int totalCount() {
		return boardDAO.totalCount();
	}

	@Override
	@Transactional(rollbackFor= {Exception.class})
	public int insertBoard(Board board, List<Attachment> attachList) {
		int result = 0;
		try {			
			result = boardDAO.insertBoard(board);
			
			int boardNo = board.getBoardNo();
			//board의 boardNo필드에 새로부여받은 pk를 가져옴.
			logger.debug("boardNo@service="+boardNo);
			
			if(attachList.size()>0) {
				for(Attachment a:attachList) {		
					a.setBoardNo(boardNo);
					result = boardDAO.insertAttachment(a);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return result;
	}

	@Override
	public Board selectBoardView(int boardNo) {
		return boardDAO.selectBoardView(boardNo);
	}

	@Override
	public List<Map<String, String>> selectAttachView(int boardNo) {
		return boardDAO.selectAttachView(boardNo);
	}

}
