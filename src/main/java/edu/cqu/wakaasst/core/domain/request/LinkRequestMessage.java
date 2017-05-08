package edu.cqu.wakaasst.core.domain.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "link_req_msg")
public class LinkRequestMessage extends BaseRequestMessage {
	
	@Column(name = "title", length = WakaAsstConfig.COL_LEN_TITLE, nullable = false)
	protected String title;
	
	@Column(name = "description", length = WakaAsstConfig.COL_LEN_CONTENT, nullable = false)
	protected String description;
	
	@Column(name = "url", length = WakaAsstConfig.COL_LEN_URL, nullable = false)
	protected String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
