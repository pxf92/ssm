package pxf.weixin.enums;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public enum ResultEnum {

    SUCCESS("0000","成功",true),
    FAIL("1111","失败"),

    ORDER_NULL("0001","订单号为空"),
    BUSINESS_CODE_NULL("0002","业务码为空"),
    REQUEST_MESSAGE_NULL("0003","消息内容不能为空"),

    ;

    private String code;
    private String message;
    private boolean success = false;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnum(String code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
