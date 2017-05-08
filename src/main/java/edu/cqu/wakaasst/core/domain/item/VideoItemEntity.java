package edu.cqu.wakaasst.core.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseMediaItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "video_item")
public class VideoItemEntity extends BaseMediaItemEntity {

	@Column(name = "media_id", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = true)
	protected String mediaId;
	
	@Column(name = "title", length = WakaAsstConfig.COL_LEN_TITLE, nullable = true)
	protected String title;
	
	@Column(name = "description", length = WakaAsstConfig.COL_LEN_CONTENT, nullable = true)
	protected String description;
	
	@ManyToOne
	@JoinColumn(name = "thumb_id", nullable = true)
	protected ThumbItemEntity thumb;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

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

	public ThumbItemEntity getThumb() {
		return thumb;
	}

	public void setThumb(ThumbItemEntity thumb) {
		this.thumb = thumb;
	}
	
}
