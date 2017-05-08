package edu.cqu.wakaasst.core.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseMediaItemEntity;

@Entity
@Table(name=WakaAsstConfig.TABLE_PREFIX + "voice_item")
public class VoiceItemEntity extends BaseMediaItemEntity {

	@Column(name = "media_id", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = true)
	protected String mediaId;
	
	@Column(name = "format", length = 10, nullable = true)
	protected String format;
	
	@Column(name = "recognition", length = WakaAsstConfig.COL_LEN_TITLE, nullable = true)
	protected String recognition;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
}
