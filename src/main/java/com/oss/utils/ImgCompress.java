package com.oss.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** 
 * 图片压缩处理 
 * @author 崔素强 
 */  
public class ImgCompress {  
    public static void getFileOutPutStream(InputStream input ,int w ,int h,OutputStream out) throws IOException{
    	 Thumbnails.of(input).size(w, h).outputFormat("jpg").toOutputStream(out);
         out.flush();
         out.close();
    }
}  
