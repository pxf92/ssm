package pxf.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author pxf
 * 2017年11月26日
 */
public class MD5Util {

	/**
	 * 加密盐
	 */
	private static final String  salt = "hadshdnas";
	
	private static final String charset = "UTF-8";
	
	public static String digist(String text){
		try {
			return DigestUtils.md5DigestAsHex((text+salt).getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return null;
		}		
	}
	
}
