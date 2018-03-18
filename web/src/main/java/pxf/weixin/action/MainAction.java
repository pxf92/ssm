package pxf.weixin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pxf.weixin.model.request.WxValidation;
import pxf.weixin.service.AccessTokenService;
import pxf.weixin.service.impl.AccessTokenServiceImpl;

@Controller
public class MainAction {

    private static final Logger log = LoggerFactory.getLogger(MainAction.class);

    @RequestMapping("main")
    @ResponseBody
    public String main(){
        return "success";
    }

    @RequestMapping("validate")
    @ResponseBody
    public String validate(WxValidation wxValidation){


        return "success";
    }

}
