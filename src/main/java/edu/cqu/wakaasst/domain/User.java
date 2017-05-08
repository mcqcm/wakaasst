package edu.cqu.wakaasst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.http.client.CookieStore;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "user")
public class User extends BaseEntity {
	
	@Column(name = "open_id", nullable = false)
	protected String openId;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "password")
	protected String password;
	
	@Column(name = "stu_seria")
	protected String stuSeria;
	
	@Column(name = "is_valid")
	protected Boolean isValid;

	@Column(name = "identityid")
	protected String identityid;
	

	public String getIdentityid() {
		return identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStuSeria() {
		return stuSeria;
	}

	public void setStuSeria(String stuSeria) {
		this.stuSeria = stuSeria;
	}

}
