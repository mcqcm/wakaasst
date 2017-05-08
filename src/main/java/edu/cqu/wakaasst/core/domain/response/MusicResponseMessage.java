package edu.cqu.wakaasst.core.domain.response;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.domain.item.MusicItemEntity;
import edu.cqu.wakaasst.core.domain.item.ThumbItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "music_resp_msg")
public class MusicResponseMessage extends BaseResponseMessage {

	@ManyToOne
	@JoinColumn(name = "music_id", nullable = false)
	protected MusicItemEntity music;
	
	@ManyToOne
	@JoinColumn(name = "thumb_id", nullable = false)
	protected ThumbItemEntity thumb;
	
	public MusicItemEntity getMusic() {
		return music;
	}

	public void setMusic(MusicItemEntity music) {
		this.music = music;
	}

	public ThumbItemEntity getThumb() {
		return thumb;
	}

	public void setThumb(ThumbItemEntity thumb) {
		this.thumb = thumb;
	}
	
}
