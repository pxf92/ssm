package pxf.weixin.model;

/**
 * 基本响应类
 * @author pxf
 * 2017年11月26日
 */
public class BaseResult {

	private boolean success;
	
	private String code;
	
	private String msg;
	
	private Object data;

	public BaseResult(boolean success){
		this.success = success;
	}
	
	public BaseResult() {
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}	
	
}
