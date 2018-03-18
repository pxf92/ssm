package pxf.weixin.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pxf.weixin.conts.LoginConstant;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SingleLoginFliter implements Filter,ApplicationContextAware{

	private Log log = LogFactory.getLog(this.getClass());
	//登录页面url
	private static String LOGIN_PATH = "http://localhost/login.html";
	//不需登陆检查的url
	private String NO_FILTERS="/login,/register,/resetPassword,/main";

	private ApplicationContext applicationContext;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		log.info("开始登陆验证");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
//		response.setContentType("charset");

		String uri = request.getRequestURI();
		String[] noFilters = NO_FILTERS.split(",");
		for (String temp : noFilters) {
			if(uri.indexOf(temp) !=-1){
				log.info("跳过登陆验证："+uri);
				//继续执行下一个过滤链
				chain.doFilter(servletRequest, servletResponse);
				return ;
			}
		}

		if(!isLoggedIn(request)){
			log.error("当前用户未登陆");
			response.sendRedirect(LOGIN_PATH);
			return ;
		}

		log.info("结束登陆验证");
		//继续执行下一个过滤链
		chain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 判断是否已登陆
	 * @param request
	 * @return
	 */
	private boolean isLoggedIn(HttpServletRequest request) {
		boolean logged=false;
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			return false;
		}
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String value = cookie.getValue();
			// TODO 判断是否登录成功
			if(cookieName.equals(LoginConstant.TOKEN_NAME) && value.equals("asdjasndj")){
                logged = true;
            }
		}
		return logged;
	}

	public void init(FilterConfig arg0) throws ServletException {
		log.info("初始化登陆验证功能");
	}

	public void afterPropertiesSet() throws Exception {

	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
