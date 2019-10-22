package com.oss.jindu;

import com.oss.utils.BucketNameType;
import com.oss.utils.OssUtil;
import com.oss.utils.PictureResult;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 平台通用文件上传
 * 将文件上传到资源文件服务器中
 * 如果要使用此工具类，需在对应的web项目中的 resources 文件夹中
 * 创建对应的 client.properties 配置文件  内容为：
 * tracker_server = 106.14.44.167:22122
 * 
 * @author 卷神
 * @version 创建时间：2016年12月8日 下午3:56:03
 */
public class UploadFilesUtilJindu {
    /**
     * 上传到图片OSS服务器
     * @version 创建时间：2016年12月8日 下午4:03:26
     * @param picFile
     * @return
     */
    public static PictureResult uploadPic(HttpServletRequest request, File picFile) {
        PictureResult result = new PictureResult();
        try {
            String url = OssUtilJindu.uploadFile(request,picFile, BucketNameType.PIC_ARTICLE);
            //返回上传阿里服务器后返回的地址
            //把url响应给客户端
            result.setError(0);
            result.setUrl(url);
            //删除文件
            deleteFile(picFile);
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败");
        }
        System.out.println(result.getUrl());
        return result;
    }
	
	/**
     * 删除上次生成的二维码文件
     */
    public static void deleteFile(File file){
        System.gc();//手动回收垃圾，清空文件占用情况，解决无法删除文件
        if(file.isFile() && file.exists()){//表示该文件不是文件夹
            file.delete();
        }else if(file.isDirectory()){
            //首先得到当前的路径
            String[] childFilePaths = file.list();
            for(String childFilePath : childFilePaths){
                File childFile=new File(file.getAbsolutePath()+File.separator+childFilePath);
                deleteFile(childFile);
            }
            file.delete();
        }
    }
}
