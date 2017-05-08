package edu.cqu.wakaasst.core.domain.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "loc_req_msg")
public class LocationRequestMessage extends BaseRequestMessage {
	
	@Column(name = "location_x", nullable = false)
	protected Double locationX;
	
	@Column(name = "location_y", nullable = false)
	protected Double locationY;
	
	@Column(name = "scale", nullable = false)
	protected Double scale;
	
	@Column(name = "label", length = WakaAsstConfig.COL_LEN_TITLE, nullable = false)
	protected String label;

	public Double getLocationX() {
		return locationX;
	}

	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}

	public Double getLocationY() {
		return locationY;
	}

	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
