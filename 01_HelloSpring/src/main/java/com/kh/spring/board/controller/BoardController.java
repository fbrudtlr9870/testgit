package com.kh.spring.board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.exception.BoardException;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/board/boardList.do")
	public ModelAndView selectBoardList(@RequestParam(value="cPage", required=false,defaultValue="1") int cPage) {
		ModelAndView mav = new ModelAndView();
		//Rowbounds처리를 위해서 offset, limit값이 필요함.
		int numperPage = 10; //=> limit
		
		//1. 현재페이지 컨텐츠 구하기
		List<Map<String,String>> list = boardService.selectEmpList(cPage,numperPage);
		System.out.println("list@boradController="+list);
		//2. 페이지바 처리를 위한 전체 컨텐츠 수 구하기
		int totalCount = boardService.totalCount();
		System.out.println("totalCount="+totalCount);
		
		mav.addObject("list",list);
		mav.addObject("totalCount",totalCount);
		mav.addObject("numperPage",numperPage);
		
		return mav;
	}
	
	@RequestMapping("/board/boardForm.do")
	public void boardForm() {
		//ViewNameTranslator가 자동으로 view단 지정	
	}
	
	@RequestMapping("/board/boardFormEnd.do")
	public ModelAndView insertBoard(Board board,@RequestParam(value="upFile",required=false) MultipartFile[] upFiles
									,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		logger.debug("게시판 페이지저장");
//		logger.debug("board="+board);
//		logger.debug("upFile="+upFile.getName());
//		logger.debug("upFile OriginalFileName="+upFile.getOriginalFilename());
//		logger.debug("size="+upFile.getSize());
		logger.debug("upFiles.length="+upFiles.length);
		logger.debug("upFile1="+upFiles[0].getOriginalFilename());
		logger.debug("upFile2="+upFiles[1].getOriginalFilename());
		
		try {
			
			//1. 파일업로드처리
			String saveDirectory = request.getSession().getServletContext().getRealPath("/resources/upload/board");
			List<Attachment> attachList = new ArrayList<>();
			
			/****************MultipartFile을 이용한 파일업로드 처리로직 시작********************/
			for(MultipartFile f:upFiles) {
				if(!f.isEmpty()) {
					//파일명 재생성
					String originalFileName = f.getOriginalFilename();
					String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
					int rndNum = (int)(Math.random()*1000);
					String renamedFileName = sdf.format(new Date(System.currentTimeMillis()))+"_"+rndNum+"."+ext;
					
					try {
						f.transferTo(new File(saveDirectory+"/"+renamedFileName));
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
					
					//VO객체 담기
					
					Attachment attach = new Attachment();
					attach.setOriginalFileName(originalFileName);
					attach.setRenamedFileName(renamedFileName);
					
					attachList.add(attach);
				}
			}
			/****************MultipartFile을 이용한 파일업로드 처리로직 끝********************/
			logger.debug("attachList="+attachList);
			
			//2. 비지니스로직
			int result = boardService.insertBoard(board,attachList);
			int boardNo = board.getBoardNo();
			logger.debug("boardNo@controller="+boardNo);
			
			//3. view단 분기
			String loc = "/";
			String msg = "";
			
			if(result>0) {
				msg = "게시물 등록성공";
				loc="/board/boardView.do?no="+boardNo;
			}else {
				msg="게시물 등록실패";
			}
			
			mav.addObject("msg",msg);
			mav.addObject("loc",loc);
			mav.setViewName("common/msg");
			
		}catch(Exception e) {
			throw new BoardException("게시물등록오류");
		}
		
		
		return mav;
	}
	
	@RequestMapping("/board/boardView.do")
	public ModelAndView selectBoardView(@RequestParam(value="no") int boardNo) {
		ModelAndView mav = new ModelAndView();
		Board b = boardService.selectBoardView(boardNo);
		List<Map<String,String>> attachList = boardService.selectAttachView(boardNo);
		
		System.out.println("board@controller"+b);
		System.out.println("attach@controller"+attachList);
		
		mav.addObject("board",b);
		mav.addObject("attachList",attachList);		
		mav.setViewName("/board/boardView");
		return mav;
	}
	
	@RequestMapping("/board/boardDownload.do")
	public void fileDownload(@RequestParam String oName
							,@RequestParam String rName
							,HttpServletRequest request
							,HttpServletResponse response) {
		logger.debug("파일다운로드페이지");
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;
		String saveDirectory = request.getSession().getServletContext().getRealPath("/resources/upload/board");
		
		File savedFile = new File(saveDirectory+"/"+rName);
		
		try {
			bis = new BufferedInputStream(new FileInputStream(savedFile));
			sos = response.getOutputStream();
			//응답세팅
			response.setContentType("application/octet-stream; charset=utf-8");
			
			//한글파일명 처리
			String resFilename="";
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE")!=-1||request.getHeader("user-agent").indexOf("Trident")!=-1;
			if(isMSIE) {
				//ie는 utf-8인코딩을 명시적으로 해줌.
				resFilename = URLEncoder.encode(oName, "utf-8");
				resFilename = resFilename.replaceAll("\\+", "%20");
			}else {
				resFilename = new String(oName.getBytes("utf-8"),"ISO-8859-1");
			}
			logger.debug("resFilename="+resFilename);
			
			response.addHeader("Content-Disposition", "attachment; filename=\""+resFilename+"\"");

			//쓰기
			int read=0;
			while((read=bis.read())!=-1) {
				sos.write(read);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
				bis.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
