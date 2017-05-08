package edu.cqu.wakaasst.core;

public enum WeChatReqMsgType {
	
	TEXT(WakaAsstConfig.WeChatReqMsgType.TEXT), 
	IMAGE(WakaAsstConfig.WeChatReqMsgType.IMAGE), 
	LOCATION(WakaAsstConfig.WeChatReqMsgType.LOCATION), 
	LINK(WakaAsstConfig.WeChatReqMsgType.LINK), 
	EVENT(WakaAsstConfig.WeChatReqMsgType.EVENT), 
	VIDEO(WakaAsstConfig.WeChatReqMsgType.VIDEO), 
	VOICE(WakaAsstConfig.WeChatReqMsgType.VOICE);
	
	private WeChatReqMsgType(final String text) {
		this._text = text;
	}

	private final String _text;

	@Override
	public String toString() {
		return _text;
	}
	
	public static WeChatReqMsgType inst(String strVal) {
		for (WeChatReqMsgType type : WeChatReqMsgType.values()) {
			if (type.toString().equalsIgnoreCase(strVal)) {
				return type;
			}
		}
		return null;
	}
	
}
