package com.yj.springboot.service.context;


import com.yj.springboot.entity.User;
import com.yj.springboot.service.context.SessionUser;
import com.yj.springboot.service.utils.SequentialUuidHexGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MultivaluedMap;
import java.net.InetAddress;
import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p>ecmp平台上下文工具类</p>
 *
 * @author <a href="mailto:chao2.ma@changhong.com">马超(Vision.Mac)</a>
 * @version 1.0.1 2017/3/30 17:07
 */
@SuppressWarnings("unchecked")
public class ContextUtil  {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContextUtil.class);

	//InheritableThreadLocal
	private static ThreadLocal<SessionUser> userTokenHold = new InheritableThreadLocal<>();

	public static final String REQUEST_TOKEN_KEY = "_s";
	public static final String AUTHORIZATION_KEY = "Authorization";
	public static final String HEADER_APPCODE_KEY = "AppCode";







	/**
	 * 获取当前服务器IP
	 *
	 * @return 当前服务器ip
	 */
	public static String getHost() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取当前会话用ID
	 *
	 * @return 返回当前会话用户ID。无会话信息，则返回anonymous
	 */
	public static String getUserId() {
		return getSessionUser().getUserId();
	}

	/**
	 * 获取当前会话用户账号
	 *
	 * @return 返回当前会话用户账号。无会话信息，则返回anonymous
	 */
	public static String getUserAccount() {
		return getSessionUser().getAccount();
	}

	/**
	 * 获取当前会话用户名
	 *
	 * @return 返回当前会话用户名。无会话信息，则返回anonymous
	 */
	public static String getUserName() {
		return getSessionUser().getUserName();
	}

	/**
	 * 获取当前会话用户信息
	 * 返回格式：
	 * 租户,用户名[账号]
	 *
	 * @return 返回当前会话用户信息。无会话信息，则返回anonymous
	 */
	public static String getUserInfo() {
		return getSessionUser().getUserInfo();
	}

	/**
	 * 获取当前会话租户代码
	 *
	 * @return 返回当前租户代码
	 */
	public static String getTenantCode() {
		return getSessionUser().getTenantCode();
	}

	/**
	 * @return 返回当前会话用户
	 */
	public static SessionUser getSessionUser() {
		SessionUser sessionUser = userTokenHold.get();
		if (sessionUser == null) {
			sessionUser = new SessionUser();
		}

        /*Thread currentThread = Thread.currentThread();
        LOGGER.debug(currentThread.getName() + "  |  " + currentThread.toString() + "  |  " + sessionUser);*/
		return sessionUser;
	}

	/**
	 * @param user 设置用户会话信息
	 */
	public static void setSessionUser(SessionUser user) {
		if (user != null) {
			userTokenHold.set(user);
		} else {
			throw new RuntimeException("设置会话用户时，SessionUser不能为空。");
		}
	}

	/**
	 * 是否匿名用户
	 *
	 * @return 返回true，则匿名用户；反之非匿名用户
	 */
	public static boolean isAnonymous() {
		boolean isAnonymous = true;
		SessionUser sessionUser = getSessionUser();
		if (sessionUser != null && !sessionUser.isAnonymous()) {
			isAnonymous = false;
		}
		return isAnonymous;
	}

	/**
	 * 清楚用户token信息
	 */
	public static void cleanUserToken() {
		userTokenHold.remove();
	}

	/**
	 * @return 返回AccessToken
	 */
	public static String getAccessToken() {
		return getSessionUser().getAccessToken();
	}



	public static MultivaluedMap<String, Object> setAuthorization(String token, MultivaluedMap<String, Object> headers) {
		if (StringUtils.isNotBlank(token) && Objects.nonNull(headers)) {
			headers.add(AUTHORIZATION_KEY, token);
		}
		return headers;
	}

	public static String getAuthorization(MultivaluedMap<String, String> headers) {
		List<String> authorizationLines = headers.get(AUTHORIZATION_KEY);
		if (authorizationLines != null && !authorizationLines.isEmpty()) {
			return authorizationLines.get(0);
		}
		return null;
	}

	public static String generateToken(SessionUser sessionUser) {
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		String randomKey = sessionUser.getSessionId();
		if (StringUtils.isBlank(randomKey)) {
			randomKey = SequentialUuidHexGenerator.generate();
			sessionUser.setSessionId(randomKey);
		}
		Map<String, Object> claims = new HashMap<>();
		claims.put("appId", sessionUser.getAppId());
		claims.put("tenant", sessionUser.getTenantCode());
		claims.put("account", sessionUser.getAccount());
		claims.put("userId", sessionUser.getUserId());
		claims.put("userName", sessionUser.getUserName());
		claims.put("email", sessionUser.getEmail());
		claims.put("ip", sessionUser.getIp());
		String token = jwtTokenUtil.generateToken(sessionUser.getAccount(), randomKey, claims);
		return token;
	}

	public static SessionUser getSessionUser(String token) {
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();


		SessionUser sessionUser = new SessionUser();
		try {
			Claims claims = jwtTokenUtil.getClaimFromToken(token);
			sessionUser.setSessionId(claims.get(JwtTokenUtil.RANDOM_KEY, String.class));
			sessionUser.setAppId(claims.get("appId", String.class));
			sessionUser.setAccessToken(token);
			sessionUser.setTenantCode(claims.get("tenant", String.class));
			sessionUser.setAccount(claims.get("account", String.class));
			sessionUser.setUserId(claims.get("userId", String.class));
			sessionUser.setUserName(claims.get("userName", String.class));
			sessionUser.setEmail(claims.get("email", String.class));
			sessionUser.setIp(claims.get("ip", String.class));
			ContextUtil.setSessionUser(sessionUser);
		} catch (ExpiredJwtException e) {
			LOGGER.error("token已过期", e);
		} catch (Exception e) {
			LOGGER.error("错误的token", e);
		}
		return sessionUser;
	}

	///////////////////////////////Mock User////////////////////////////////

	/**
	 * 模拟用户
	 *
	 * @return 返回会话id
	 */
	public static SessionUser mockUser() {
		return mockUser(SequentialUuidHexGenerator.generate());
	}

	public static SessionUser mockUser(String sessionId) {
		String tenant = "tenantCode";
		String account = "admin";
		return getSessionUser(sessionId, tenant, account);
	}

	public static SessionUser setSessionUser(String tenant, String account) {
		return getSessionUser(SequentialUuidHexGenerator.generate(), tenant, account);
	}

	private static SessionUser getSessionUser(String sessionId, String tenant, String account) {
		if (StringUtils.isBlank(sessionId)) {
			throw new IllegalArgumentException("模拟登录用户时，会话ID不能为空！");
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantCode", StringUtils.isNotBlank(tenant) ? tenant : "");
		params.put("account", account);
		// todo  通过http请求获取用户
//		String path = "userAccount/findByAccountAndTenantCode";
//		UserInfo userInfo = ApiClient.getEntityViaProxy(ConfigConstants.BASIC_API, path, UserInfo.class, params);
		SessionUser sessionUser = new SessionUser();
		User user = new User();
		sessionUser.setUserId(user.getId());
		sessionUser.setAccount(account);
		sessionUser.setUserName(user.getName());
		sessionUser.setTenantCode("tenantCode");
		sessionUser.setLoginTime(new Date());
		sessionUser.setSessionId(sessionId);

		String accessToken = generateToken(sessionUser);
		sessionUser.setAccessToken(accessToken);

		ContextUtil.setSessionUser(sessionUser);
		return sessionUser;
	}
}
