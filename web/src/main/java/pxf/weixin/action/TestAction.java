package pxf.weixin.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pxf.weixin.service.AccessTokenService;

/**
 * @Author pxf
 * @Date 2018/3/17
 * @Description
 */
@Controller
public class TestAction {

    @Autowired
    private AccessTokenService accessTokenService;

    @RequestMapping("getAccessToken")
    @ResponseBody
    public String getAccessToken() {
        accessTokenService.refreshAccessToken();
        return "success";
    }

}
