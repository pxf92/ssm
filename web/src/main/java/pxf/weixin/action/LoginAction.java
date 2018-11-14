package pxf.weixin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pxf.weixin.model.User;

/**
 * @Author pxf
 * @Date 2018/8/25
 * @Description
 */
@Controller
public class LoginAction {

    private Logger log = LoggerFactory.getLogger(LoginAction.class);

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(ModelMap modelMap,User user){

        modelMap.addAttribute("user",user);
        modelMap.addAttribute("msg","登录成功");
        return "success";
    }

}
