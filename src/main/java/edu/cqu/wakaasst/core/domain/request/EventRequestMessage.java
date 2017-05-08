package edu.cqu.wakaasst.core.domain.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "event_req_msg")
public class EventRequestMessage extends BaseRequestMessage {

	@Column(name = "event", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = false)
	protected String event;
	
	@Column(name = "event_key", length = WakaAsstConfig.COL_LEN_TITLE, nullable = true)
	protected String eventKey;
	
	@Column(name = "ticket", length = WakaAsstConfig.COL_LEN_TITLE, nullable = true)
	protected String ticket;
	
	@Column(name = "latitude")
	protected Double latitude;
	
	@Column(name = "longitude")
	protected Double longitude;
	
	@Column(name = "myprecision")
	protected Double precision;
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	
}
