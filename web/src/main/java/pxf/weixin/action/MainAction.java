package pxf.weixin.action;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pxf.utils.DateUtil;
import pxf.weixin.business.BusinessManager;
import pxf.weixin.enums.BusinessCodeEnum;
import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;
import pxf.weixin.result.request.WxValidation;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Controller
public class MainAction {

    private static final Logger log = LoggerFactory.getLogger(MainAction.class);

    @Autowired
    private BusinessManager businessManager;

    @RequestMapping("main")
    @ResponseBody
    public String main(){
        return "success";
    }

    @RequestMapping("inquiry")
    @ResponseBody
    public String inquiry(String requestMsg){
        String message = null;
        try {
            byte[] bytes = requestMsg.getBytes("ISO-8859-1");
            message = new String(bytes, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            return "error";
        }

        InnerRequest innerRequest = new InnerRequest();
        innerRequest.setBusinessCode(BusinessCodeEnum.MESSAGE_REPLY.getCode());
        innerRequest.setRequestMsg(message);
        innerRequest.setOrderId("M"+DateUtil.detailLSHFormat(new Date()));

        BaseResult baseResult = businessManager.doBusiness(innerRequest);

        return JSON.toJSONString(baseResult);
    }

}
