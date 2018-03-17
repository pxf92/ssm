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
import pxf.weixin.conts.TargetConts;
import pxf.weixin.conts.WeChatConts;
import pxf.weixin.model.response.AccessTokenResp;
import pxf.weixin.model.response.BaseWechatResp;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件，可以指定多个配置文件，locations指定的是一个数组
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class BaseTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpConnClient httpConnClient;

    @Test
    public void aaa(){
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


    private void bbb(Map map){

    }
}
