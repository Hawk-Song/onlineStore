package com.store.util;

public class PathUtil {
    private static String seperator = System.getProperty("file.separator");//get separator os current os
    
    /** 
    * @Description: get root directory of imgs 
    * @Param:  
    * @return:
    * @Author: Hawk.Song 
    * @Date: 6/16/18 
    */ 
    //this is util class, we do not need to instantiate it, call its methods directly. so use static
    public static String getImgBasePath(){
        String os = System.getProperty("os.name"); //choose root path according to os
        //why not set classpath as root directory?
        //because if you save pic in classpath, once you deploy it again, newly generated pic will be delete, unless the
        //pics are in classpath at the very beginning. this is impossible, because user need to upload their own imgs.
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath="D:/projectdev/image/";
        } else {
            basePath = "/Users/savokisong/Downloads/image/";
        }
        basePath = basePath.replace("/", seperator);// window path separator is \, while unix is /
        return basePath;
    }
    
    
    /** 
    * @Description: get the path of img of given shop
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 6/16/18 
    */ 
    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
