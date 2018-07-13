package com.store.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.ShopExecution;
import com.store.entity.Area;
import com.store.entity.PersonInfo;
import com.store.entity.Shop;
import com.store.entity.ShopCategory;
import com.store.enums.ShopStateEnum;
import com.store.service.AreaService;
import com.store.service.ShopCategoryService;
import com.store.service.ShopService;
import com.store.util.CodeUtil;
import com.store.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value="/getshopbyid", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                try {
                    Shop shop = shopService.getByshopId(shopId);
                    List<Area> areaList = areaService.getAreaList();
                    modelMap.put("shop", shop);
                    modelMap.put("areaList", areaList);
                    modelMap.put("success", true);
                } catch(Exception e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "empty shopId");
                }
            }
        return modelMap;
    }

    @RequestMapping(value="getshopinitinfo", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory>  shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap; //modelMap then will be convert to json by ResponseBody
    }

    //HttpSerlvetRequest: request from front end
    @RequestMapping(value="/registershop", method=RequestMethod.POST)
    @ResponseBody //convert to json
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //captcha
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong captcha");
            return modelMap;
        }

        //get information from front end, convert it to object
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //get file stream from front end, put into shopImg
        CommonsMultipartFile shopImg = null;
        //file resolver: resolve file information in request
        //get content from this session's context
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //check if there file stream in request
        if(commonsMultipartResolver.isMultipart(request)) {
            //force to covert request to MulitpartHttpServletRequest
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //then extract file stream from request, so that can be processed by spring
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "must upload a img");
            return modelMap;
        }

        //register shop
        if(shop != null && shopImg !=null) {
            //where is the user from
            // before operate shop, user need to login
            //when log in, write user info to session
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;

            try {
                se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if(se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if(shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please input shop information");
            return modelMap;
        }

    }

    @RequestMapping(value="/modifyshop", method=RequestMethod.POST)
    @ResponseBody //convert to json
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //captcha
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong captcha");
            return modelMap;
        }

        //get information from front end, convert it to object
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //get file stream from front end, put into shopImg
        CommonsMultipartFile shopImg = null;
        //file resolver: resolve file information in request
        //get content from this session's context
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //check if there file stream in request
        if(commonsMultipartResolver.isMultipart(request)) {
            //force to covert request to MulitpartHttpServletRequest
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //then extract file stream from request, so that can be processed by spring
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        //update shop information
        if(shop != null && shop.getShopId() != null) {

            ShopExecution se = null;

            try {
                if(shopImg == null) {
                    se = shopService.modifyShop(shop, null, null);
                } else {
                    se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                }
                if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }


            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please input shop information");
            return modelMap;
        }

    }




//    private static void inputStreamToFile(InputStream ins, File file){
//        FileOutputStream os = null;
//        try{
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while((bytesRead = ins.read(buffer)) != -1){
//                os.write(buffer, 0, bytesRead);
//            }
//        } catch(Exception e) {
//            throw new RuntimeException("Exception happens in inputStreamToFile" + e.getMessage());
//        } finally {
//            try{
//                if(os != null){
//                    os.close();
//                }
//                if(ins != null) {
//                    ins.close();
//                }
//            } catch(IOException e) {
//                throw new RuntimeException("Exception happens when close i/o in inputStreamToFile" + e.getMessage());
//            }
//        }
//
//    }
}
