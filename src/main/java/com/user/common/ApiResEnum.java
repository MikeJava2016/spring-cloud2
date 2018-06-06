package com.user.common;

public enum ApiResEnum {
	success(200),
	fail(404),
	
	
	
	
	
	
	
	
	
	
	
	;
	private int code;
	private String reason;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	private ApiResEnum(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}
	private ApiResEnum(int code) {
		this.code = code;
	}
	
}
