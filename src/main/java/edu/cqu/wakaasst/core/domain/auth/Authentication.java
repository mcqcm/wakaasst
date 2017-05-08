package edu.cqu.wakaasst.core.domain.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "authentication")
public class Authentication extends BaseEntity {

	@Column(name = "grant_type", length = 50, nullable = false)
	protected String grantType;
	
	@Column(name = "appid", length = 100, nullable = false)
	protected String appid;
	
	@Column(name = "secret", length = 100, nullable = false)
	protected String secret;

	@Column(name = "access_token", length = 200, nullable = false)
	protected String accessToken;
	
	@Column(name = "expires_in", nullable = false)
	protected Long expiresIn;

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
