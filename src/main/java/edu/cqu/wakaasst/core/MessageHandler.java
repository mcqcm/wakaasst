package edu.cqu.wakaasst.core;

import java.util.Map;

import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;

public interface MessageHandler {
	
	WeChatReqMsgType[] support();
	
	BaseResponseMessage handle(BaseRequestMessage msg, Map<String, Object> context);
	
	Integer priority();
	
}
