package pxf.weixin.action;

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
public class UserAction {

    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(){

        return "user/register";
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String register(ModelMap modelMap, User user){
        modelMap.addAttribute("msg","注册成功");
        return "success";
    }
}
