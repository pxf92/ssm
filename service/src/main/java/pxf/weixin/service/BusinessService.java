package pxf.weixin.service;

import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public interface BusinessService {

    BaseResult doBusiness(BaseResult baseResult,InnerRequest innerRequest);

}
