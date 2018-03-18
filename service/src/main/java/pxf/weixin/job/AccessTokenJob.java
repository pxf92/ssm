package pxf.weixin.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pxf.weixin.service.AccessTokenService;

import java.io.Serializable;

/**
 * @Author pxf
 * @Date 2018/3/17
 * @Description
 */
public class AccessTokenJob implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(AccessTokenJob.class);

    private static final long serialVersionUID = 7087935327610328808L;

    @Autowired
    private AccessTokenService accessTokenService;

    public void execute() {
        log.info("任务开始执行");
        accessTokenService.refreshAccessToken();
        log.info("任务执行完成");
    }

}
