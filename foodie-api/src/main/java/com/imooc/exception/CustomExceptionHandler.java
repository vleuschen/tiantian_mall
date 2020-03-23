package com.imooc.exception;

import com.immoc.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {


    //上传文件超过500k捕获异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handleMaxUpdateFile(MaxUploadSizeExceededException exception) {
        return JSONResult.errorMap("文件上传大小不能超过500k,请压缩图片或降低图片质量上传");
    }

}
