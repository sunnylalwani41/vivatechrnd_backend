package com.vivatechrnd.exception;

import java.time.LocalDateTime;

public class MyErrorDetail {
	private String message;
	private String detail;
	private LocalDateTime localDateTime;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	
	public MyErrorDetail(String message, String detail, LocalDateTime localDateTime) {
		super();
		this.message = message;
		this.detail = detail;
		this.localDateTime = localDateTime;
	}
	public MyErrorDetail() {
		super();
	}
}
