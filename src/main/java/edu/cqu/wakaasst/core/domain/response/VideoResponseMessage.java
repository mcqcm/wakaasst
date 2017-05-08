package edu.cqu.wakaasst.core.domain.response;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.domain.item.VideoItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "video_resp_msg")
public class VideoResponseMessage extends BaseResponseMessage {

	@ManyToOne
	@JoinColumn(name = "video_id")
	protected VideoItemEntity video;

	public VideoItemEntity getVideo() {
		return video;
	}

	public void setVideo(VideoItemEntity video) {
		this.video = video;
	}
	
}
