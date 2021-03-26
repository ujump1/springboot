package com.yj.springboot.service.config;

import com.alibaba.fastjson.JSONObject;
import com.yj.springboot.entity.annotation.IgnoreCheckAuth;
import com.yj.springboot.service.context.ContextUtil;
import com.yj.springboot.service.context.SessionUser;
import com.yj.springboot.service.exception.BusinessException;
import com.yj.springboot.service.exception.HttpStatusEnum;
import com.yj.springboot.service.responseModel.ResponseModel;
import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description: 拦截器
 * @author: yj
 * @create: 2020/8/18
 */
@Component
public class SessionUserTokenInterceptor extends HandlerInterceptorAdapter {
    public static final String AUTHORIZATION_KEY = "Authorization";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 打印此次http请求线程的会话信息，可以发现每次都是空的
        System.out.println(JsonUtils.toJson(ContextUtil.getSessionUser()));
        // 暂时先全部返回成功，且模拟用户登录
        if(1==1) {
            ContextUtil.mockUser();
            return true;
        }
        // 获取请求
        String requestPath = request.getRequestURI();
        // swagger 文档跳过不检查token
        if (StringUtils.containsAny(requestPath, "/v2/api-docs", "/swagger-","/webjars")) {
            return true;
        }
        // 请求放过
        if (ignoreAuthForOut(requestPath)) {
            return true;
        }
        // 注解放过
        if (handler instanceof HandlerMethod) {
            //如果有IgnoreCheckAuth注解的不做登录用户
            Annotation[] annotations = ((HandlerMethod) handler).getBeanType().getAnnotations();
            if (!ObjectUtils.isEmpty(annotations)) {
                for (int i = 0; i < annotations.length; i++) {
                    if (annotations[i].annotationType().getName().equalsIgnoreCase(IgnoreCheckAuth.class.getName())){
                        return true;
                    }
                }
            }
        }
        String token = request.getHeader(AUTHORIZATION_KEY);
        SessionUser sessionUser;

        if (!StringUtils.isBlank(token)) {
            sessionUser = ContextUtil.getSessionUser(token); //根据token设置当前用户
        } else {
            // 特殊方法模拟登录
            if (ignoreAuth(requestPath)) {
                try {
                    sessionUser = ContextUtil.setSessionUser("tenantCode", "admin"); //模拟登录
                }catch (Exception e) {
                    //非法token
                    throw new BusinessException(HttpStatusEnum.AUTHENTICATION_EXCEPTION.getStatus()
                            , HttpStatusEnum.AUTHENTICATION_EXCEPTION.getMessage() + ":" + token, e);
                }
            } else {
                try {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                    ResponseModel responseMode = ResponseModel.ACCESS_FORBIDDEN();
                    writer.write(JsonUtils.toJson(responseMode));
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前请求是否需要忽略权限(新希望在线)
     * @param path
     * @return
     */
    public boolean ignoreAuth(String path){
        List<String> ignorePaths = new ArrayList<>();
        ignorePaths.add("/businessDocumentRequests/pageRequest");
        ignorePaths.add("/businessDocumentsUninvoiced/searchAndGather");
        ignorePaths.add("/invoice-kind-assign/");
        ignorePaths.add("/businessDocumentsUninvoiced/createRequest");
        ignorePaths.add("/businessDocumentsUninvoiced/unCanceledList");
        ignorePaths.add("/businessDocumentsUninvoiced/canceledList");
        ignorePaths.add("/businessDocumentsUninvoiced/canceledGatherList");
        ignorePaths.add("/businessDocumentsUninvoiced/moveInOrOut");
        ignorePaths.add("/businessDocumentsUninvoiced/setupDetailDiscountAmount");
        ignorePaths.add("/businessDocumentsUninvoiced/createDetailInvoice");
        ignorePaths.add("/businessDocumentsUninvoiced/queryAllDrawHeader");
        ignorePaths.add("/businessDocumentsUninvoiced/previewInvoice");
        ignorePaths.add("/condition/startLbpmFlow");
        ignorePaths.add("/tax-rate-assign/page/vague");
        ignorePaths.add("/businessDocumentRequests/delete/list");
        return ignorePaths.stream().anyMatch((t) -> path.contains(t));
    }


    /**
     * 判断当前请求是否需要忽略权限(对外接口和下载等)
     * @param path
     * @return
     */
    public boolean ignoreAuthForOut(String path){
        List<String> ignorePaths = new ArrayList<>();
        ignorePaths.add("/error");
        ignorePaths.add("/monitor/health");
        ignorePaths.add("/invoice-collection-service/invoiceQuerySimple");
        ignorePaths.add("/invoice-collection-service/invoiceQueryByCondition");
        ignorePaths.add("/invoice-collection-service/invoiceQuery");
        ignorePaths.add("/invoice-collection-service/invoiceAccountQuery");
        ignorePaths.add("/invoice-collection-service/invoiceAccountWriteback");
        ignorePaths.add("/invoice-collection-service/signAccount");
        ignorePaths.add("/invoice-collection-service/listInvoicePoolsByInvoiceNumber");
        ignorePaths.add("/invoice-collection-service/listInvoicePoolsByInvoiceCode");
        ignorePaths.add("/invoice-collection-service/queryInvoicePoolsToTax");
        ignorePaths.add("/draw-interface-service/queryDrawInvoicedToTax");
        ignorePaths.add("/draw-interface-service/writeOff");
        ignorePaths.add("/invoice-collection-service/electInvoiceImport");
        ignorePaths.add("/invoice-collection-service/ocrImport");
        ignorePaths.add("/template/download");
        ignorePaths.add("/invoicePools/exportInvoicePoolsReport");
        ignorePaths.add("/invoice-collection-service/signQuery");
        ignorePaths.add("/invoice-collection-service/signOccupiedInvoice");
        ignorePaths.add("/invoice-kind/listAllInvoiceKind");

        return ignorePaths.stream().anyMatch((t) -> path.contains(t));
    }

    // 请求结束返回给客户端前清除会话信息
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogUtil.info("清除会话。。。");
        ContextUtil.cleanUserToken();
        super.afterCompletion(request, response, handler, ex);
    }
}
