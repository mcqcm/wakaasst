package edu.cqu.wakaasst.core.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseMediaItemEntity;

@Entity
@Table(name=WakaAsstConfig.TABLE_PREFIX + "music_item")
public class MusicItemEntity extends BaseMediaItemEntity {

	@Column(name = "media_id", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = true)
	protected String mediaId;
	
	@Column(name="title", length = WakaAsstConfig.COL_LEN_TITLE, nullable = true)
	protected String title;
	
	@Column(name="description", length = WakaAsstConfig.COL_LEN_CONTENT, nullable = true)
	protected String description;
	
	@Column(name="music_url", length = WakaAsstConfig.COL_LEN_URL, nullable = true)
	protected String musicUrl;
	
	@Column(name="hq_music_url", length = WakaAsstConfig.COL_LEN_URL, nullable = true)
	protected String hqMusicUrl;

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

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	
}
