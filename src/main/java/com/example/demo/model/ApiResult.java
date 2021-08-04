package com.example.demo.model;

import lombok.*;

@Data
public class ApiResult<T> {
	public String ErrorCode;
	public String Message;
	public T Data;

	public ApiResult<T> ApiSuccess(T Data) {
		this.ErrorCode = "0";
		this.Message = "";
		this.Data = Data;
		return this;
	}
	public ApiResult<T> ApiNotFound() {
		this.ErrorCode = "204";
		this.Message = "查無資料";
		this.Data = null;
		return this;
	}
	public ApiResult<T> ApiOtherErr(String ErrCode,String ErrMsg)
	{
		this.ErrorCode = ErrCode;
		this.Message = ErrMsg;
		this.Data = null;
		return this;
	}
}
