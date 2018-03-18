package pxf.weixin.request;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public class InnerRequest {

    private String businessCode;

    private String orderId;

    private String requestMsg;

    private String[] requestMsges;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public String[] getRequestMsges() {
        return requestMsges;
    }

    public void setRequestMsges(String[] requestMsges) {
        this.requestMsges = requestMsges;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
