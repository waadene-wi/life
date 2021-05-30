package com.wy.life.utils;

import lombok.Data;

@Data
public class Result {
	
	public static final String ERROR = "999";
	public static final String SUCC = "000";
	
	private String code;
	private String msg;
	public Object data;
	
	public static Result success() {
		return new Result(SUCC, "");
	}	
	public static Result error(String msg) {
		return new Result(ERROR, msg);
	}	
	public Result(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
