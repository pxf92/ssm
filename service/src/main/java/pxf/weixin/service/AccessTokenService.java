package pxf.weixin.service;

import pxf.weixin.result.response.AccessTokenResp;

/**
 * @Author pxf
 * @Description
 * @Date 2018/3/17
 */
public interface AccessTokenService {

    public AccessTokenResp refreshAccessToken();

    public String getAccessToken();

}
