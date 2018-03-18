package pxf.weixin.validator;

import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public interface ValidatorService {

    BaseResult validate(BaseResult baseResult,InnerRequest innerRequest);

}
