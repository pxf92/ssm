package pxf.weixin.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexAction {

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "welcome";
    }

}
