package com.common;

public class ApiResponse<T> {
	private int code;
	private String reason;
	private T data;
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
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ApiResponse(int code, String reason, T data) {
		super();
		this.code = code;
		this.reason = reason;
		this.data = data;
	}
	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiResponse(int code, String reason) {
		super();
		this.code = code;
		this.reason = reason;
	}
	
	public ApiResponse(ApiResEnum apiResEnum) {
		super();
		this.code = apiResEnum.getCode();
		this.reason = apiResEnum.getReason();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static ApiResponse successResponse() {
		return new ApiResponse(ApiResEnum.success);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ApiResponse successResponse(Object data) {
		ApiResponse apiResponse = new ApiResponse(ApiResEnum.success);
		apiResponse.setData(data);
		return apiResponse;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static ApiResponse failResponse(ApiResEnum apiResEnum) {
		return new ApiResponse(apiResEnum);
	}
	
}
