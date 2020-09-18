package com.yj.springboot.service.exception;

import com.yj.springboot.service.utils.LogUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 全局异常处理
 * @author: wangdayin
 * @create: 2018/8/13.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义异常处理
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(MessageRuntimeException.class)
    public ErrorMessage<String> illegalPropExceptionHandler(HttpServletRequest request, MessageRuntimeException exception) throws Exception {
        return handleErrorInfo(request, exception.getMessage(), exception);
    }

    /**
     * org.springframework.dao.DataIntegrityViolationException
     * @param request
     * @param exception
     * @return
     * @throws DataIntegrityViolationException
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorMessage<String> illegalIndexExceptionHandler(HttpServletRequest request, DataIntegrityViolationException exception) throws Exception {
        if(!StringUtils.isEmpty(exception.getMessage()) && exception.getMessage().contains("IDX_")){
            return handleErrorInfo(request, "数据重复" , exception);
        }
        return handleErrorInfo(request, "系统错误，请联系管理员-" + exception.getMessage(), exception);
    }
    /**
     * @param e       BindException
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ErrorMessage<String> handleBindException(BindException e, HttpServletRequest request) {
        //获取参数校验错误集合
        List<FieldError> fieldErrors = e.getFieldErrors();
        //格式化以错误提示
        String message = String.format("参数校验错误（%s）：%s", fieldErrors.size(),
                fieldErrors.stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(";")));
        return handleErrorInfo(request, message, e);
    }


    /**
     * 顶级异常处理
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ErrorMessage<String> exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        return handleErrorInfo(request, exception.getMessage(), exception);
    }

    /**
     * 统一返回处理
     *
     * @param request
     * @param message
     * @param exception
     * @return
     */
    private ErrorMessage<String> handleErrorInfo(HttpServletRequest request, String message, Exception exception) {
        ErrorMessage<String> errorMessage=new ErrorMessage<String>();
        errorMessage.setMessage(message);
        errorMessage.setStatus(ErrorMessage.ERROR);
        errorMessage.setData(message);
        errorMessage.setUrl(request.getRequestURL().toString());
        return errorMessage;
    }
}
