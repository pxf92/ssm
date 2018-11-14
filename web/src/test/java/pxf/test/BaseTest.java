package pxf.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pxf.test.model.User;
import pxf.utils.XMLUtil;

import java.util.concurrent.Executors;

public class BaseTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void aaa(){
//        User user = new User(1,"pxf","20");
//        String xml = XMLUtil.object2Xml(user);

//        log.info(xml);

        new Thread();
        Executors.newFixedThreadPool(3);
    }

}
