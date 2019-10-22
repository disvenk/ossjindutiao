package com.oss.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.oss.jindu.UploadFilesUtilJindu;
import com.oss.utils.FileUpload;
import com.oss.utils.PictureResult;
import com.oss.utils.Result;
import com.oss.utils.UploadFilesUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by disvenk.dai on 2018-12-11 18:22
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @RequestMapping("setCookie")
    @ResponseBody
    public String set(HttpServletRequest request){
        request.getSession().setAttribute("cookie","代绍文");
        return "set";
    }

    @RequestMapping("getCookie")
    @ResponseBody
    public String get(HttpServletRequest request){
        System.out.println(request.getSession().getAttribute("cookie"));
        return "get";
    }

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "/h5index";
    }

    @RequestMapping("file")
    @ResponseBody
    public Result uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        //String type = request.getParameter("type");
        String systemPath = request.getServletContext().getRealPath("");
        systemPath = systemPath.replaceAll("\\\\", "/");
        int lastR = systemPath.lastIndexOf("/");
        systemPath = systemPath.substring(0, lastR) + "/";
        String filePath = "upload/files/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        File finalFile = FileUpload.fileUp(file, systemPath + filePath, UUID.randomUUID().toString(), "true");
        //测试
//        OSSClient ossClient = new OSSClient("https://oss-cn-shanghai.aliyuncs.com", "LTAIRTuAQCP1sqDG", "jPC6yvVyqX1vFcSTeyjgLrRmSIh46W");
//        PutObjectResult putObjectResult =  ossClient.putObject(new PutObjectRequest("canjia", "test.jpg", finalFile));
//        putObjectResult.getETag();

        PictureResult picResult = UploadFilesUtilJindu.uploadPic(request,finalFile);
        if(picResult.getError() == 0){
            return new Result(picResult.getUrl(),true);
        }else{
            return new Result(false);
        }
    }

    /**
     * 获取实时长传进度
     * @param request
     * @return
     */
    @RequestMapping ("item/percent")
    @ResponseBody
    public int getUploadPercent(HttpServletRequest request){
        HttpSession session = request.getSession();
        int percent = session.getAttribute("upload_percent") == null ? 0: (int) session.getAttribute("upload_percent");
        return percent;
    }

    /**
     * 重置上传进度
     * @param request
     * @return
     */
    @RequestMapping ("/percent/reset")
    public void resetPercent(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("upload_percent",0);
    }
}
