package pxf.weixin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.utils.DateUtil;
import pxf.weixin.conts.WeChatConts;
import pxf.weixin.dao.AccessTokenMapper;
import pxf.weixin.enums.KeyType;
import pxf.weixin.manager.redis.RedisManager;
import pxf.weixin.result.AccessToken;
import pxf.weixin.result.response.AccessTokenResp;
import pxf.weixin.service.AccessTokenService;
import pxf.weixin.service.CommonService;

import java.util.Date;

/**
 * @Author pxf
 * @Date 2018/3/17
 * @Description
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger log = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private AccessTokenMapper accessTokenMapper;
    /**
     * token有效期（秒）
     */
    private static final int dalay = 7200;

    public AccessTokenResp refreshAccessToken() {
        AccessTokenResp accessToken = null;
        try {
            accessToken = commonService.getAccessToken();
            if(accessToken != null){
                AccessToken record = new AccessToken();
                record.setAccessToken(accessToken.getAccess_token());
                record.setAppid(WeChatConts.appid);

                try {
                    Date date = new Date();
                    Date expireTime = DateUtil.addSecond(date, Integer.parseInt(accessToken.getExpires_in()));
                    record.setExpireTime(expireTime);
                } catch (NumberFormatException e) {
                    log.error("时间格式错误："+accessToken.getExpires_in(),e);
                    record.setExpireTime(DateUtil.addSecond(new Date(), dalay));
                }

                int i = accessTokenMapper.updateByPrimaryKey(record);
                if( i==0 ){
                    accessTokenMapper.insert(record);
                }

                try {
                    redisManager.setString(KeyType.TOKEN,record.getAccessToken(), dalay);
                } catch (Exception e) {
                    log.error("token加入缓存失败",e);
                }

            }
        } catch (Exception e) {
            log.error("获取access_token失败",e);
        }

        return accessToken;
    }

    public String getAccessToken() {
        try {
            String accessToken = redisManager.getString(KeyType.TOKEN);
            if(accessToken != null){
                return accessToken;
            }
        } catch (Exception e) {
            log.error("查询token缓存失败",e);
        }

        AccessToken accessToken = accessTokenMapper.selectByPrimaryKey(WeChatConts.appid);
        //未获取到token或者token失效
        if(accessToken == null || accessToken.getExpireTime().before(new Date())){
            return refreshAccessToken().getAccess_token();
        }

        return accessToken.getAccessToken();
    }

}
