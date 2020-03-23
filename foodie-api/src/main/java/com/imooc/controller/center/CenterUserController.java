package com.imooc.controller.center;

import com.immoc.utils.CookieUtils;
import com.immoc.utils.DateUtil;
import com.immoc.utils.JSONResult;
import com.immoc.utils.JsonUtils;
import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resources.FileUpload;
import com.imooc.service.center.CenterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息接口",tags = {"用户信息接口"})
@RestController
@RequestMapping("/userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@ApiParam(name="userId",value = "用户id",required = true)
                                   @RequestParam String userId,
                               @RequestBody @Valid CenterUserBO centerUserBO,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               HttpServletResponse response){
        // 判断BindingResult是否包含保存错误的验证消息，如果有，则直接return
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = getErrors(bindingResult);
            return JSONResult.errorMap(errorMap);
        }

        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        // TODO 后续要改，增加令牌token，整合进redis，分布式会话

        return JSONResult.ok();

    }

    private Map<String,String> getErrors(BindingResult bindingResult){

        List<FieldError> errors = bindingResult.getFieldErrors();

        Map<String,String> map = new HashMap<>();
        for (FieldError error : errors) {
            //发生验证错误所对应的某一个属性
            String errorField = error.getField();
            //验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField,errorMsg);
        }
        return map;
    }

    @ApiOperation(value = "修改用户头像",notes = "修改用户头像",httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(@ApiParam(name="userId",value = "用户id",required = true)
                             @RequestParam String userId,
                             @ApiParam(name = "file",value = "用户头像",required = true)
                             MultipartFile file,
                             HttpServletRequest request,
                           HttpServletResponse response){

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String uploadPathPredfix = "";
        try {
            //定义头像保存的地址
//            String fileSpace = IMAGE_USER_FACE_LOCATION;

            String fileSpace = fileUpload.getImageUserFaceLocation();

            //在路径上为每个用户增加一个用户id，用于区分不同的用户上传
            uploadPathPredfix = File.separator + userId;

            if(file != null){
                //获得上传文件的文件名称
                String fileName = file.getOriginalFilename();
                if(StringUtils.isNoneBlank(fileName)){
                    //文件重命名imooc-face.png
                    String[] fileNameArr = fileName.split("\\.");

                    //获取文件的后缀名
                    String suffix = fileNameArr[fileNameArr.length-1];

                    //
                    if ( !suffix.equalsIgnoreCase("png") &&
                         !suffix.equalsIgnoreCase("jpg") &&
                         !suffix.equalsIgnoreCase("jpeg")){
                        return JSONResult.errorMap("图片格式不正确！");
                    }

                    //face-[userid].png
                    //文件名称重组 覆盖式上传，如果使用增量式 后面可以加上时间戳
                    String newFileName = "face-"+userId + "." + suffix;

                    //设置上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPredfix + File.separator + newFileName;

                    //用于给Web服务访问的地址
                    uploadPathPredfix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile() !=  null){
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    //进行文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                }
            }else{
                return JSONResult.errorMap("文件不能为空！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileOutputStream != null || inputStream !=null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String imageServerUrl = fileUpload.getImageServerUrl();

        //由于浏览器可能存在缓存的情况，所以在这里加上时间戳保证更新后的图片可以及时刷新
        String finaleUserFaceUrl = imageServerUrl + uploadPathPredfix +
                "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        //更新用户头像到数据库
        Users userResult = centerUserService.updateUserFace(userId,finaleUserFaceUrl);

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);
        // TODO 后续要改，增加令牌token，整合进redis，分布式会话


        return JSONResult.ok();
    }


    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setRealname(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);

        return userResult;
    }
}
