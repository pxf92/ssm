package pxf.weixin.business.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.weixin.business.BusinessManager;
import pxf.weixin.enums.ResultEnum;
import pxf.weixin.factory.BusinessBeanFactory;
import pxf.weixin.processor.Processor;
import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;
import pxf.weixin.service.BusinessService;
import pxf.weixin.validator.ValidatorService;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
@Service
public class BusinessManagerImpl implements BusinessManager {

    private static final Logger log = LoggerFactory.getLogger(BusinessManagerImpl.class);
    @Autowired
    private BusinessBeanFactory businessBeanFactory;

    public BaseResult doBusiness(InnerRequest innerRequest) {
        BaseResult baseResult = new BaseResult(true);
        try {
            log.info("请求参数：{}", JSON.toJSONString(innerRequest));
            String businessCode = innerRequest.getBusinessCode();

            ValidatorService validator = businessBeanFactory.getValidator(businessCode);
            baseResult = validator.validate(baseResult, innerRequest);
            if(!baseResult.isSuccess()){
                log.info("校验失败:[{}],[{}]",innerRequest.getOrderId(),baseResult.getMsg());
                return baseResult;
            }

            Processor processor = businessBeanFactory.getProcessor(businessCode);
            innerRequest = processor.process(innerRequest);

            BusinessService businessService = businessBeanFactory.getService(businessCode);
            baseResult = businessService.doBusiness(baseResult,innerRequest);

        } catch (Exception e) {
            log.error("业务处理失败："+innerRequest.getOrderId(),e);
            baseResult.buildResult(ResultEnum.FAIL);
        }
        log.info("响应参数：{}", JSON.toJSONString(baseResult));
        return baseResult;
    }

}
