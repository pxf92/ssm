package pxf.weixin.processor;

import pxf.weixin.request.InnerRequest;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
public interface Processor {

    InnerRequest process(InnerRequest innerRequest);

}
