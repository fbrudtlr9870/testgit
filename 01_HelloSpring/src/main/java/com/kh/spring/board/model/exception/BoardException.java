package com.kh.spring.board.model.exception;

public class BoardException extends RuntimeException {
	public BoardException() {
		super();
	}
	
	public BoardException(String message) {
		super(message);
	}
}
