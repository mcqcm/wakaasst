package edu.cqu.wakaasst.core.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import edu.cqu.wakaasst.core.WakaAsstConfig;

@MappedSuperclass
public abstract class BaseResponseMessage extends BaseEntity {

	@Column(name="func_flag", nullable = false)
	protected Integer funcFlag;
	
	@Column(name = "to_user_name", length = WakaAsstConfig.COL_LEN_USER_NAME, nullable = false)
	protected String toUserName;
	
	@Column(name = "from_user_name", length = WakaAsstConfig.COL_LEN_USER_NAME, nullable = false)
	protected String fromUserName;
	
	@Column(name = "msg_created_time", nullable = false)
	protected Long msgCreatedTime;
	
	@Column(name = "msg_type", length = WakaAsstConfig.COL_LEN_INDICATOR, nullable = false)
	protected String msgType;
	
	public Integer getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(Integer funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getMsgCreatedTime() {
		return msgCreatedTime;
	}

	public void setMsgCreatedTime(Long msgCreatedTime) {
		this.msgCreatedTime = msgCreatedTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
}
