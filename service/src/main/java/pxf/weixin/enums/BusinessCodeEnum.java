package pxf.weixin.enums;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public enum BusinessCodeEnum {

    MESSAGE_REPLY("MESSAGE_REPLY","消息回复","messageReply"),
    ;

    private String code;

    private String name;

    private String beanName;

    BusinessCodeEnum(String code, String name, String beanName) {
        this.code = code;
        this.name = name;
        this.beanName = beanName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public static BusinessCodeEnum getByCode(String businessCode) {
        for (BusinessCodeEnum businessCodeEnum:values()) {
            if(businessCodeEnum.getCode().equals(businessCode)){
                return businessCodeEnum;
            }
        }
        return null;
    }

}
