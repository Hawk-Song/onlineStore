package com.store;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* @Description:  configure spring with junit. when junit start, load springIOC container
* @Author: Hawk.Song
* @Date: 6/15/18 
*/

@RunWith(SpringJUnit4ClassRunner.class)
//tell junit where the spring configuration file is
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {
    
}
