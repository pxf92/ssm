package pxf.weixin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.weixin.dao.MessageReplyConfigMapper;
import pxf.weixin.enums.ResultEnum;
import pxf.weixin.request.InnerRequest;
import pxf.weixin.result.BaseResult;
import pxf.weixin.service.BusinessService;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description 响应消息服务
 */
@Service("messageReplyService")
public class MessageReplyServiceImpl implements BusinessService {

    private static final Logger log = LoggerFactory.getLogger(MessageReplyServiceImpl.class);

    @Autowired
    private MessageReplyConfigMapper messageReplyConfigMapper;

    public BaseResult doBusiness(BaseResult baseResult, InnerRequest innerRequest) {

        String reply = reply(innerRequest.getRequestMsges());
        if(reply != null){
            baseResult.setData(reply);
        }else{
            baseResult.setData("您好，如果有什么需要咨询的，请拨打客服电话0571-1234567");
        }

        return baseResult;
    }

    /**
     * 生成响应消息
     * @param message
     * @return
     */
    private String reply(String[] message) {
        try {
            for (String keyWord : message) {
                String responseMsg = messageReplyConfigMapper.selectResponseMsg(keyWord);
                if(responseMsg != null){
                    return responseMsg;
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }

        return null;
    }

}
