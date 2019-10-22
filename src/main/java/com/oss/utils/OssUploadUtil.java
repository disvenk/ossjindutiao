//package com.oss.utils;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.event.ProgressEvent;
//import com.aliyun.oss.event.ProgressEventType;
//import com.aliyun.oss.event.ProgressListener;
//import com.aliyun.oss.model.PutObjectRequest;
//import com.aliyun.oss.model.PutObjectResult;
//import org.apache.logging.log4j.util.PropertiesUtil;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.util.Map;
//
///**
// * Created by disvenk.dai on 2018-12-11 18:41
// */
//public class OssUploadUtil {
//
//    /**
//     * 带进度的上传
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    public static String uploadPicWithProgress(MultipartFile file, HttpServletRequest request) throws Exception {
//        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//        String accessKeyId = "LTAINfbQqw049TwX";
//        String accessKeySecret = "B2xY7kzv6bkRJi8K1yUsLPYE3bjoD7";
//        String bucketName = "pic-article";
//        DefaultMultipartHttpServletRequest req = (DefaultMultipartHttpServletRequest) request;
//        request.setCharacterEncoding("UTF-8");
//        int i = 0;
//        StringBuffer totalPath = new StringBuffer();
//
//            OSSClient oSSClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//
//            try {
//         /*
//         这里用带进度条的OSS上传
//         将session传入PutObjectProgressListener的构造中!官网例子是没有这个操作的
//         注意new PutObjectRequest()的第三个参数是File而不是官网介绍的FileInputStream,否则获取不到进度.
//        */
//                PutObjectResult putObjectResult = oSSClient.putObject(new PutObjectRequest(bucketName, fileName, f).
//                        <PutObjectRequest>withProgressListener(new PutObjectProgressListener(request.getSession())));
//            } catch (OSSException oe) {
//                System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                        + "but was rejected with an error response for some reason.Error Message: " + oe.getErrorCode()
//                        + "Error Code:" + oe.getErrorCode() + "Request ID:" + oe.getRequestId() + "Host ID:" + oe.getHostId());
//                throw new OSSException(oe.getErrorMessage(), oe);
//            } catch (ClientException ce) {
//                System.out.println("Caught an ClientException, which means the client encountered "
//                        + "a serious internal problem while trying to communicate with OSS, "
//                        + "such as not being able to access the network.Error Message:" + ce.getMessage());
//                throw new ClientException(ce);
//            } finally {
//                oSSClient.shutdown();
//            }
//            // totalPath.append(filePath + SEPARATOR + ossPath + fileName + ",");
//            totalPath.deleteCharAt(totalPath.length() - 1);
//            return totalPath.toString();
//
//        return "";
//    }
//}
