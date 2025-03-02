package pers.leanfeng.score.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pers.leanfeng.score.core.configuration.ExceptionCodeConfiguration;
import pers.leanfeng.score.exception.http.HttpException;


import java.util.List;

// 全局异常捕获类
// @ExceptionHandler 对应相应异常的处理方法
@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    ExceptionCodeConfiguration codesConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)     //状态码
    public UnifyResponse handleException(HttpServletRequest req,Exception e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        System.out.println(e);
        UnifyResponse message = new UnifyResponse(9999,"服务器异常",method+" "+requestUrl);
        return message;
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e){
        String requestUrl = req.getRequestURI();    // 获取请求的 url
        String method = req.getMethod();    // 获取请求方法 GET/POST

        //从配置文件获取错误码对应的消息
        UnifyResponse message = new UnifyResponse(e.getCode(), codesConfiguration.getMessage(e.getCode()), method+" "+requestUrl);
        HttpHeaders headers = new HttpHeaders();
        // 创建响应头
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());  //设置状态码
        // ResponseEntity用于自定义响应体，包括响应头、状态码等。
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message, headers, httpStatus);
        return r;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(10001,message,method+" "+requestUrl);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        String message = e.getMessage();
//        System.out.println(e.getMessage());
//        for (ConstraintViolation error:e.getConstraintViolations()){
//            error.getMessage();
//        }
        return new UnifyResponse(10001,message,method+" "+requestUrl);
    }

    private String formatAllErrorMessages(List<ObjectError> errors){
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(
                error->errorMsg.append(error.getDefaultMessage()).append(";")
        );
        return errorMsg.toString();
    }
}
