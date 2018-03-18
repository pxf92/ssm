package pxf.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pxf.utils.HttpConnClient;
import pxf.utils.HttpUtil;
import pxf.utils.MessageUtil;
import pxf.utils.enums.MessageFormat;
import pxf.weixin.conts.TargetConts;
import pxf.weixin.conts.WeChatConts;
import pxf.weixin.model.response.AccessTokenResp;
import pxf.weixin.model.response.BaseWechatResp;
import pxf.weixin.service.AccessTokenService;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class BaseTest {

    private static Logger log = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    private HttpConnClient httpConnClient;
    @Autowired
    private AccessTokenService accessTokenService;

    @Test
    public void testGet(){
        try {
            String target = TargetConts.access_token;

            Map<String ,String> map = new HashMap<String, String>();
            map.put("grant_type", WeChatConts.access_token_value);
            map.put("appid",WeChatConts.appid);
            map.put("secret",WeChatConts.secret);
            String queryString = HttpUtil.getQueryString(map);

            Map<String, String> resp = httpConnClient.get(target, queryString);
            String respData = resp.get("respData");
            BaseWechatResp baseWechatResp = JSON.parseObject(respData, AccessTokenResp.class);

            return ;
        } catch (Exception e) {
            log.error("",e);
        }
    }

    @Test
    public void testGetToken(){
        log.info("查询token:{}",accessTokenService.getAccessToken());
    }

    @Test
    public void testMessage(){

        String[] params = new String[4];
        params[0]="123456";//接收方帐号（收到的OpenID）
        params[1]="666666";//开发者微信号
        params[2]="20180318";//发送时间
        params[3]="测试";//回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）

        String xml = MessageUtil.convert2xml(MessageFormat.TEXT_MESSAGE, params);

        log.warn("xml:{}",xml);

    }


}
