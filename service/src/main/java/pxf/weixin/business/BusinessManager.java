package pxf.weixin.business;

import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public interface BusinessManager {

    BaseResult doBusiness(InnerRequest innerRequest);

}
