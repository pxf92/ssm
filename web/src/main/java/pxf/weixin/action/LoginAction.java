package pxf.weixin.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pxf.weixin.conts.LoginConstant;
import pxf.weixin.result.BaseResult;
import pxf.weixin.result.User;
import pxf.weixin.user.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginAction {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UserService userService;

	private @Value("${cookie.timeout:86400}")int cookieTimeout;

	@RequestMapping(value="login")
	@ResponseBody
	public  String login(ModelMap model,HttpServletRequest request,HttpServletResponse response, User user){
		
		BaseResult result = userService.login(user);
		if(!result.isSuccess()){
			log.info("-----login fail!");
			model.addAttribute("message", result.getMsg());
			model.addAttribute("user",user);
			return result.getMsg();
		}

		addLoggedCookie(response,user.getName());
		
		model.addAttribute("user", user);
		log.info("-----login success!");
		return "login success!";
	}

	private void addLoggedCookie(HttpServletResponse response, String name) {
		//添加cookie，10分钟有效
		Cookie cookie = new Cookie(LoginConstant.TOKEN_NAME, LoginConstant.TOKEN_VALUE);
		cookie.setMaxAge(cookieTimeout);
		response.addCookie(cookie);

		Cookie userCookie = new Cookie(LoginConstant.USER_COOKIE_NAME, name);
		userCookie.setMaxAge(cookieTimeout);
		response.addCookie(userCookie);
	}

	@RequestMapping(value = "resetPassword")
	public String resetPassword2(String name, String auth, String pwd, ModelMap model) {
		if(!auth.equals("xfreset")){
			return "fail";
		}

		BaseResult result = userService.resetPassword(name, null, pwd, false);
		if(!result.isSuccess()){
			String message = result.getMsg();
			log.error(message);
			model.addAttribute("message", message);
			return "error";
		}
		return "success";
	}

}
