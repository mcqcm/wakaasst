package edu.cqu.wakaasst.core.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseMediaItemEntity;

@Entity
@Table(name=WakaAsstConfig.TABLE_PREFIX + "thumb_item")
public class ThumbItemEntity extends BaseMediaItemEntity {

	@Column(name = "media_id", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = true)
	protected String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
