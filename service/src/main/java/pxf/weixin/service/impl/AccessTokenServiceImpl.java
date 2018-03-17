package pxf.weixin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxf.utils.DateUtil;
import pxf.weixin.conts.WeChatConts;
import pxf.weixin.dao.AccessTokenMapper;
import pxf.weixin.model.AccessToken;
import pxf.weixin.model.response.AccessTokenResp;
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
    private AccessTokenMapper accessTokenMapper;

    public AccessTokenResp getAccessToken() {
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
                    record.setExpireTime(DateUtil.addSecond(new Date(), 7200));
                }

                int i = accessTokenMapper.updateByPrimaryKey(record);
                if( i==0 ){
                    accessTokenMapper.insert(record);
                }
            }
        } catch (Exception e) {
            log.error("获取access_token失败",e);
        }

        return accessToken;
    }
}
