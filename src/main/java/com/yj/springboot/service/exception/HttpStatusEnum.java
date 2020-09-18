package com.yj.springboot.service.exception;

/**
 * 200: '服务器成功返回请求的数据。',
 * 201: '新建或修改数据成功。',
 * 202: '一个请求已经进入后台排队（异步任务）。',
 * 204: '删除数据成功。',
 * 400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
 * 401: '用户没有权限（令牌、用户名、密码错误）。',
 * 403: '用户得到授权，但是访问是被禁止的。',
 * 404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
 * 406: '请求的格式不可得。',
 * 410: '请求的资源被永久删除，且不会再得到的。',
 * 422: '当创建一个对象时，发生一个验证错误。',
 * 500: '服务器发生错误，请检查服务器。',
 * 502: '网关错误。',
 * 503: '服务不可用，服务器暂时过载或维护。',
 * 504: '网关超时。',
 * <p>
 * author: lifei
 * date: 2018/8/23
 */
public enum HttpStatusEnum {

    SUCCESS(200, "访问成功"),
    REQUEST_EXCEPTION(400, "请求有错误"),
    UN_AUTHENTICATION_EXCEPTION(401, "请重新登录！"),
    AUTHENTICATION_EXCEPTION(403, "访问是被禁止"),
    SERVER_EXCEPTION(500, "服务器发生错误"),
    ALREADE_EXIST(204,"字段类型[type_code]和字典值[value]已经存在，请重新设置"),
    ALREADE_EXIST_ANALOG(205,"该合同类型已经保存了借方!"),
    NOT_UPDATE(206,"承担公司为非本公司，无法修改!"),;


    HttpStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private Integer status;

    private String message;

    public Integer getStatus() {
        return status;
    }

    public HttpStatusEnum setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
