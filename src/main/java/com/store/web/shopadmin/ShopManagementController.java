package com.store.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin", method={RequestMethod.GET})
public class ShopManagementController {

    @RequestMapping(value="/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
        //why not html/shop?
        //because in spring configuration file spring-web.xml
        //InternalResourceViewResolver, we have set prefix value="/WEB-INF/html/"
    }
}