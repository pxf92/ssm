package pxf.weixin.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pxf.weixin.enums.BusinessCodeEnum;
import pxf.weixin.processor.Processor;
import pxf.weixin.service.BusinessService;
import pxf.weixin.validator.ValidatorService;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
@Service
public class BusinessBeanFactory {

    private static final Logger log = LoggerFactory.getLogger(BusinessBeanFactory.class);

    @Autowired
    private ApplicationContext ap;

    private static final String validatorFrepix = "Validator";
    private static final String processorFrepix = "Processor";
    private static final String serviceFrepix = "Service";

    public ValidatorService getValidator(String businessCode){
        ValidatorService bean = null;
        try {
            String beanName = getBeanName(businessCode,validatorFrepix);
            bean = ap.getBean(beanName, ValidatorService.class);
        } catch (BeansException e) {
            log.error("",e);
        }

        return bean;
    }
    public Processor getProcessor(String businessCode){
        Processor bean = null;
        try {
            String beanName = getBeanName(businessCode,processorFrepix);
            bean = ap.getBean(beanName, Processor.class);
        } catch (BeansException e) {
            log.error("",e);
        }

        return bean;
    }
    public BusinessService getService(String businessCode){
        BusinessService bean = null;
        try {
            String beanName = getBeanName(businessCode,serviceFrepix);
            bean = ap.getBean(beanName, BusinessService.class);
        } catch (BeansException e) {
            log.error("",e);
        }

        return bean;
    }

    private String getBeanName(String businessCode,String validatorFrepix) {
        BusinessCodeEnum businessCodeEnum = BusinessCodeEnum.getByCode(businessCode);
        return businessCodeEnum.getBeanName() + validatorFrepix;
    }

}
