package edu.cqu.wakaasst.core.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseMediaItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "pic_desc_item")
public class PicDescItemEntity extends BaseMediaItemEntity {

	@Column(name="title", length = WakaAsstConfig.COL_LEN_TITLE, nullable = false)
	protected String title;
	
	@Column(name="description", length = WakaAsstConfig.COL_LEN_CONTENT, nullable = false)
	protected String description;
	
	@Column(name="pic_url", length = WakaAsstConfig.COL_LEN_URL, nullable = false)
	protected String picUrl;
	
	@Column(name="url", length = WakaAsstConfig.COL_LEN_URL, nullable = false)
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
