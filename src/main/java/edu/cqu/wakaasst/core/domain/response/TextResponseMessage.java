package edu.cqu.wakaasst.core.domain.response;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "text_resp_msg")
public class TextResponseMessage extends BaseResponseMessage {

	@Column(name = "content", length = WakaAsstConfig.COL_LEN_CONTENT, nullable = false)
	protected String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
