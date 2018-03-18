package pxf.weixin.user;

import pxf.weixin.model.BaseResult;
import pxf.weixin.model.User;

public interface UserService {

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public BaseResult register(User user);
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	BaseResult login(User user);
	
	/**
	 * 重置密码
	 * @param name
	 * @param oldPassword
	 * @param newPassword
	 * @param needOld
	 * @return
	 */
	public BaseResult resetPassword(String name, String oldPassword, String newPassword, boolean needOld);
	
	public User selectUserByName(String name);

	public int deleteUserByName(String name);

	public int updateUserByName(User user);

	
}
