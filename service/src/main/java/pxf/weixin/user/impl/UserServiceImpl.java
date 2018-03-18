package pxf.weixin.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.utils.MD5Util;
import pxf.weixin.dao.UserMapperExt;
import pxf.weixin.result.BaseResult;
import pxf.weixin.result.User;
import pxf.weixin.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapperExt userMapperExt;
	
	public BaseResult login(User user) {
		BaseResult baseResult = new BaseResult();
		baseResult.setSuccess(true);
		try {
			User origUser = userMapperExt.selectUserByName(user.getName());
			if(origUser == null){
				baseResult.setSuccess(false);
				baseResult.setMsg("用户不存在");
				return baseResult;
			}
			
			if(!validPassword(origUser.getPassword(), user.getPassword())){
				baseResult.setSuccess(false);
				baseResult.setMsg("用户或密码不正确");
				return baseResult;
			}
			
		} catch (Exception e) {
			log.error("登录校验密码失败", e);
			baseResult.setSuccess(false);
			baseResult.setMsg("登录校验密码失败");
		}
		return baseResult;
	}
	
	public BaseResult register(User user) {
		BaseResult BaseResult = new BaseResult();
		BaseResult.setSuccess(true);
		try {
			user.setPassword(MD5Util.digist(user.getPassword()));
			userMapperExt.insert(user);
		} catch (Exception e) {
			log.error("用户注册失败",e);
			User origUser = userMapperExt.selectUserByName(user.getName());
			String message ;
			if(origUser != null){
				message = "用户已经存在";			
			}else{
				message = "服务异常，用户注册失败";				
			}
			BaseResult.setSuccess(false);
			BaseResult.setMsg(message);
		}
		return BaseResult;
	}

	public User selectUserByName(String name) {
		return userMapperExt.selectUserByName(name);
	}

	public int deleteUserByName(String name) {
		return userMapperExt.deleteByName(name);
	}

	public int updateUserByName(User user) {
		
		user.setPassword(null);
		return userMapperExt.updateByName(user);
	}
	
	public BaseResult resetPassword(String name, String oldPassword,
			String newPassword, boolean needOld) {
		BaseResult baseResult = new BaseResult(true);

		User user = userMapperExt.selectUserByName(name);
		//需要校验密码
		if(needOld){
			//校验密码
			if(!validPassword(user.getPassword(), oldPassword)){
				baseResult.setSuccess(false);
				baseResult.setMsg("原密码不正确");
				return baseResult;
			}			
		}

		//重置密码
		user.setPassword(MD5Util.digist(newPassword));
		userMapperExt.updateByName(user);
		return baseResult;
	}

	/**
	 * 校验密码
	 * @param cipherPwd 加密后的密码
	 * @param password	原始密码
	 * @return
	 */
	public boolean validPassword(String cipherPwd, String password){
		return cipherPwd.equals(MD5Util.digist(password));
	}
}
