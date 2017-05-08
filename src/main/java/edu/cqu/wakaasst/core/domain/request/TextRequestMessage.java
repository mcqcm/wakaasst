package edu.cqu.wakaasst.core.domain.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "text_req_msg")
public class TextRequestMessage extends BaseRequestMessage {

	@Column(name = "content", length = WakaAsstConfig.COL_LEN_CONTENT, nullable = false)
	protected String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
