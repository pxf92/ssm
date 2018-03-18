package pxf.weixin.result;

import pxf.weixin.enums.ResultEnum;

/**
 * 基本响应类
 * @author pxf
 * 2017年11月26日
 */
public class BaseResult<T> {

	private boolean success;
	
	private String code;
	
	private String msg;
	
	private T data;

	public BaseResult(boolean success){
		this.success = success;
	}
	
	public BaseResult() {
	}

	public void buildResult(ResultEnum resultEnum){
		this.success=resultEnum.isSuccess();
		this.code=resultEnum.getCode();
		this.msg=resultEnum.getMessage();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
