package com.yj.springboot.service.context;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <strong>实现功能：</strong>
 * <p>
 * 用户会话信息
 * 以sessionId是否为空判断用户是否登录
 * </p>
 *
 * @author <a href="mailto:chao2.ma@changhong.com">马超(Vision.Mac)</a>
 * @version 1.0.1 2017/3/30 19:24
 */
public class SessionUser implements Serializable {

	private static final long serialVersionUID = -3948903856725857866L;
	/**
	 * accessToken
	 */
	private String accessToken;
	private String sessionId;
	/**
	 * 用户id，平台唯一
	 */
	private String userId = "anonymous";
	/**
	 * 用户账号
	 */
	private String account = "anonymous";
	/**
	 * 用户名
	 */
	private String userName = "anonymous";
	/**
	 * 租户代码
	 */
	private String tenantCode;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 邮箱
	 */
	private String mobile;

	/**
	 * 客户端IP
	 */
	private String ip = "Unknown";
	/**
	 * 语言环境
	 */
	private String locale;
	/**
	 * 应用代码
	 */
	private String appId = "Unknown";
	/**
	 * 登录时间
	 */
	private Date loginTime;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonIgnore
	public String getUserInfo() {
		return toString();
	}

	@JsonIgnore
	public boolean isAnonymous() {
		return StringUtils.isBlank(getAccessToken());
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(userName).append(" [ ").append(tenantCode).append(" | ").append(account).append(" ] ");
		return str.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SessionUser)) {
			return false;
		}
		SessionUser that = (SessionUser) o;
		return Objects.equals(getAccessToken(), that.getAccessToken())
				&& Objects.equals(getUserId(), that.getUserId())
				&& Objects.equals(getTenantCode(), that.getTenantCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAccessToken(), getUserId(), getTenantCode());
	}
}
