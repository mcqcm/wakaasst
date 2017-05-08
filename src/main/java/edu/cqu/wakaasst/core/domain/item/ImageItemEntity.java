package edu.cqu.wakaasst.core.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseMediaItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "image_item")
public class ImageItemEntity extends BaseMediaItemEntity {

	@Column(name = "media_id", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = true)
	protected String mediaId;
	
	@Column(name="pic_url", length=WakaAsstConfig.COL_LEN_URL, nullable=true)
	protected String picUrl;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
