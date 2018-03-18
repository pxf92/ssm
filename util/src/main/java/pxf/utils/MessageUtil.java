package pxf.utils;

import pxf.utils.enums.MessageFormat;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public class MessageUtil {

    public static String convert2xml(MessageFormat messageFormat,String... obj){
        return String.format(messageFormat.getFormat(),obj);
    }

}
