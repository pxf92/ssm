package pxf.weixin.processor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.weixin.dao.MessageReplyConfigMapper;
import pxf.weixin.processor.Processor;
import pxf.weixin.request.InnerRequest;

import java.util.List;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
@Service("messageReplyProcessor")
public class MessageReplyProcessor implements Processor {

    @Autowired
    private MessageReplyConfigMapper messageReplyConfigMapper;
    private int wordLimit = 10;

    public InnerRequest process(InnerRequest innerRequest) {
        innerRequest.setRequestMsges(extractRequestMessage(innerRequest.getRequestMsg()));
        return innerRequest;
    }

    private String[] extractRequestMessage(String requestMsg) {
        //TODO 提取用户请求关键词

        List<String> keyWords = messageReplyConfigMapper.selectAllKeyWords();
        int size = keyWords.size();
        int length = size < wordLimit ? size : wordLimit;

        String[] messages = new String[length];
        int i=0;
        for (String keyWord : keyWords) {
            if(requestMsg.contains(keyWord) && i< length){
                messages[i++] = keyWord;
            }
        }

        return messages;
    }

}
