package pxf.weixin.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pxf.weixin.service.AccessTokenService;
import pxf.weixin.service.impl.AccessTokenServiceImpl;

/**
 * @Author pxf
 * @Date 2018/3/17
 * @Description
 */
public class AccessTokenJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AccessTokenJob.class);

    @Autowired
    private AccessTokenService accessTokenService;

    public void execute() {
        log.info("任务开始执行");
        accessTokenService.getAccessToken();
        log.info("任务执行完成");
    }

}
