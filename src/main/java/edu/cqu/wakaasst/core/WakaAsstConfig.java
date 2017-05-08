package edu.cqu.wakaasst.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WakaAsstConfig {
	
	public static final String TABLE_PREFIX = "wc_";
	public static final int COL_LEN_URL  = 1024;
	public static final int COL_LEN_CONTENT  = 4000;
	public static final int COL_LEN_TITLE  = 200;
	public static final int COL_LEN_USER_NAME  = 100;
	public static final int COL_LEN_INDICATOR  = 64;
	
	public static class WeChatReqMsgType {
		public static final String TEXT = "text";
		public static final String IMAGE = "image";
		public static final String LOCATION = "location";
		public static final String LINK = "link";
		public static final String EVENT = "event";
		public static final String VIDEO = "video";
		public static final String VOICE = "voice";
	}
	
	public static class WeChatRespMsgType {
		public static final String TEXT = "text";
		public static final String IMAGE = "image";
		public static final String VOICE = "voice";
		public static final String VIDEO = "video";
		public static final String MUSIC = "music";
		public static final String NEWS = "news";
	}
	
	@Value("#{sysProperties.wakaasst_token}")
	private String token;
	
	@Value("#{sysProperties.wakaasst_appid}") 
	private String appid;
	
	@Value("#{sysProperties.wakaasst_appsecret}")
	private String appsecret;
	
	@Value("#{sysProperties.wx_menu_create_url}")
	private String menuCreateUrl;
	
	@Value("#{sysProperties.wx_menu_get_url}")
	private String menuGetUrl;
	
	@Value("#{sysProperties.wx_menu_delete_url}")
	private String menuDeleteUrl;
	
	@Value("#{sysProperties.wx_access_token_create_url}")
	private String accessTokenCreateUrl;
	
	@Value("#{sysProperties.wx_custom_send_url}")
	private String customSendUrl;

	@Value("#{sysProperties.wx_media_upload_url}")
	private String mediaUploadUrl;
	
	@Value("#{sysProperties.wx_qrcode_create_url}")
	private String qrcodeCreateUrl;
	
	@Value("#{sysProperties.wx_user_info_url}")
	private String userInfoUrl;
	
	@Value("#{sysProperties.wx_user_get_url}") 
	private String userGetUrl;
	
	@Value("#{sysProperties.wx_groups_create_url}")
	private String groupsCreateUrl;
	
	@Value("#{sysProperties.wx_groups_get_url}")
	private String groupsGetUrl;
	
	@Value("#{sysProperties.wx_groups_getid_url}")
	private String groupsGetIdUrl;
	
	@Value("#{sysProperties.wx_groups_update_url}")
	private String groupsUpdateUrl;
	
	@Value("#{sysProperties.wx_groups_members_update_url}")
	private String groupsMembersUpdateUrl;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getAppid() {
		return appid;
	}
	
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getAppsecret() {
		return appsecret;
	}
	
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
	public String getMenuCreateUrl() {
		return menuCreateUrl;
	}
	
	public void setMenuCreateUrl(String menuCreateUrl) {
		this.menuCreateUrl = menuCreateUrl;
	}
	
	public String getMenuGetUrl() {
		return menuGetUrl;
	}
	
	public void setMenuGetUrl(String menuGetUrl) {
		this.menuGetUrl = menuGetUrl;
	}
	
	public String getMenuDeleteUrl() {
		return menuDeleteUrl;
	}
	
	public void setMenuDeleteUrl(String menuDeleteUrl) {
		this.menuDeleteUrl = menuDeleteUrl;
	}
	
	public String getAccessTokenCreateUrl() {
		return accessTokenCreateUrl;
	}
	
	public void setAccessTokenCreateUrl(String accessTokenCreateUrl) {
		this.accessTokenCreateUrl = accessTokenCreateUrl;
	}
	
	public String getCustomSendUrl() {
		return customSendUrl;
	}
	
	public void setCustomSendUrl(String customSendUrl) {
		this.customSendUrl = customSendUrl;
	}
	
	public String getMediaUploadUrl() {
		return mediaUploadUrl;
	}
	
	public void setMediaUploadUrl(String mediaUploadUrl) {
		this.mediaUploadUrl = mediaUploadUrl;
	}
	
	public String getQrcodeCreateUrl() {
		return qrcodeCreateUrl;
	}
	
	public void setQrcodeCreateUrl(String qrcodeCreateUrl) {
		this.qrcodeCreateUrl = qrcodeCreateUrl;
	}
	
	public String getUserInfoUrl() {
		return userInfoUrl;
	}
	
	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}
	
	public String getUserGetUrl() {
		return userGetUrl;
	}
	
	public void setUserGetUrl(String userGetUrl) {
		this.userGetUrl = userGetUrl;
	}
	
	public String getGroupsCreateUrl() {
		return groupsCreateUrl;
	}
	
	public void setGroupsCreateUrl(String groupsCreateUrl) {
		this.groupsCreateUrl = groupsCreateUrl;
	}
	
	public String getGroupsGetUrl() {
		return groupsGetUrl;
	}
	
	public void setGroupsGetUrl(String groupsGetUrl) {
		this.groupsGetUrl = groupsGetUrl;
	}
	
	public String getGroupsGetIdUrl() {
		return groupsGetIdUrl;
	}
	
	public void setGroupsGetIdUrl(String groupsGetIdUrl) {
		this.groupsGetIdUrl = groupsGetIdUrl;
	}
	
	public String getGroupsUpdateUrl() {
		return groupsUpdateUrl;
	}
	
	public void setGroupsUpdateUrl(String groupsUpdateUrl) {
		this.groupsUpdateUrl = groupsUpdateUrl;
	}
	
	public String getGroupsMembersUpdateUrl() {
		return groupsMembersUpdateUrl;
	}
	
	public void setGroupsMembersUpdateUrl(String groupsMembersUpdateUrl) {
		this.groupsMembersUpdateUrl = groupsMembersUpdateUrl;
	}
	
}
