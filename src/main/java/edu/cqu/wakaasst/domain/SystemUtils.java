package edu.cqu.wakaasst.domain;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SystemUtils {
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return request.getRequest();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletContext getServletContext() {
		return getSession().getServletContext();
	}
	
	public static CommonService getCommonService() {
		return getCommonService(getRequest());
	}
	
	public static CommonService getCommonService(HttpServletRequest request) {
		return getCommonService(request.getSession().getServletContext());
	}
	
	public static CommonService getCommonService(ServletContext context) {
		return WebApplicationContextUtils.getWebApplicationContext(context).getBean("commonService", CommonService.class);
	}

}
