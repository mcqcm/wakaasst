package edu.cqu.wakaasst.core.domain.request;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;
import edu.cqu.wakaasst.core.domain.item.ImageItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "image_req_msg")
public class ImageRequestMessage extends BaseRequestMessage {
	
	@ManyToOne
	@JoinColumn(name = "image_id")
	protected ImageItemEntity image;

	public ImageItemEntity getImage() {
		return image;
	}

	public void setImage(ImageItemEntity image) {
		this.image = image;
	}
	
}
