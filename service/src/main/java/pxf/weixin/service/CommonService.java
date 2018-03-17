package pxf.weixin.service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.utils.HttpConnClient;
import pxf.utils.HttpUtil;
import pxf.weixin.conts.TargetConts;
import pxf.weixin.conts.WeChatConts;
import pxf.weixin.model.response.AccessTokenResp;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pxf
 * @Date 2018/3/17
 * @Description
 */
@Service
public class CommonService {
    private static final Logger log = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private HttpConnClient httpConnClient;

    public AccessTokenResp getAccessToken() {
        try {
            String target = TargetConts.access_token;

            Map<String ,String> map = new HashMap<String, String>();
            map.put("grant_type", WeChatConts.access_token_value);
            map.put("appid",WeChatConts.appid);
            map.put("secret",WeChatConts.secret);
            String queryString = HttpUtil.getQueryString(map);

            Map<String, String> resp = httpConnClient.get(target, queryString);
            log.info("获取access_token响应内容：{}",resp);

            String respData = resp.get("respData");
            AccessTokenResp accessTokenResp = JSON.parseObject(respData, AccessTokenResp.class);

            return accessTokenResp;
        } catch (Exception e) {
            log.error("获取access_token失败",e);
        }

        return null;
    }

}
