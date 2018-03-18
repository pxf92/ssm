package pxf.weixin.validator.impl;

import pxf.weixin.enums.ResultEnum;
import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;
import pxf.weixin.validator.ValidatorService;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public abstract class AbstractValidator implements ValidatorService {

    public BaseResult validate(BaseResult baseResult, InnerRequest innerRequest) {
        if(innerRequest.getOrderId() == null){
            baseResult.buildResult(ResultEnum.ORDER_NULL);
        }else if(innerRequest.getBusinessCode() == null){
            baseResult.buildResult(ResultEnum.BUSINESS_CODE_NULL);
        }

        if(!baseResult.isSuccess()){
            return baseResult;
        }

        return specialValidate(baseResult,innerRequest);
    }

    protected abstract BaseResult specialValidate(BaseResult baseResult, InnerRequest innerRequest);
}
