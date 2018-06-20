package com.store.util;


import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import net.coobird.thumbnailator.Thumbnails;
import javax.imageio.ImageIO;

public class ImageUtil {
    //the path of classPath
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();


    /**
    * @Description:
    * @Param:thumbnail, pic file upload by users. targetAddr, where to store the processed file
    * @return:
    * @Author: Hawk.Song
    * @Date: 6/16/18
    */
    public static String generateThumbnail(File thumbnail, String targetAddr){
        //because the name of pics given by user may be duplicate, we should generate a random one
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail); // get pic extension name like png.
        makeDirPath(targetAddr);//if this directory do not exist, create it.
        String relativeAddr = targetAddr  +realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        //create thumbnail
        try{
            Thumbnails.of(thumbnail).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }
    /** 
    * @Description:  generate random file name, current month day hour minute second + five digits random number
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 6/18/18 
    */ 
    private static String getRandomFileName() {
        //get five digits number (10000 + 89999 < = 99999)
        int ranNum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + ranNum;
    }
    /** 
    * @Description: get input file stream's extension name 
    * @Param:
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 6/18/18 
    */ 
    private static String getFileExtension(File cFile){
        String originalFileName = cFile.getName();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }
    /** 
    * @Description: create directory on target path, namely, /home/work/xiangze/xxxxjpg, then all three folders need to be created
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 6/18/18 
    */ 
    private static void makeDirPath(String targetAddr){
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }


}
