package com.vivatechrnd.dto;

public class GenericResponse<T> {
	private String message;
	private Boolean success;
	private T object;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	
	public GenericResponse(String message, Boolean success, T object) {
		super();
		this.message = message;
		this.success = success;
		this.object = object;
	}
}