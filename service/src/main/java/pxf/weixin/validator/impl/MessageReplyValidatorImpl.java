package pxf.weixin.validator.impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;
import pxf.weixin.enums.ResultEnum;
import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;
import pxf.weixin.validator.ValidatorService;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
@Service("messageReplyValidator")
public class MessageReplyValidatorImpl extends AbstractValidator{

    @Override
    protected BaseResult specialValidate(BaseResult baseResult, InnerRequest innerRequest) {
        if(StringUtils.isEmpty(innerRequest.getRequestMsg())){
            baseResult.buildResult(ResultEnum.REQUEST_MESSAGE_NULL);
        }

        return baseResult;
    }

}
