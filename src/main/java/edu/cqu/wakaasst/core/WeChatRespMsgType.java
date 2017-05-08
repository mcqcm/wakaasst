package edu.cqu.wakaasst.core;

public enum WeChatRespMsgType {
	
	TEXT(WakaAsstConfig.WeChatRespMsgType.TEXT), 
	IMAGE(WakaAsstConfig.WeChatRespMsgType.IMAGE), 
	MUSIC(WakaAsstConfig.WeChatRespMsgType.MUSIC), 
	NEWS(WakaAsstConfig.WeChatRespMsgType.NEWS), 
	VIDEO(WakaAsstConfig.WeChatRespMsgType.VIDEO), 
	VOICE(WakaAsstConfig.WeChatRespMsgType.VOICE);
	
	private WeChatRespMsgType(final String text) {
		this._text = text;
	}

	private final String _text;

	@Override
	public String toString() {
		return _text;
	}
	
	public static WeChatRespMsgType inst(String strVal) {
		for (WeChatRespMsgType type : WeChatRespMsgType.values()) {
			if (type.toString().equalsIgnoreCase(strVal)) {
				return type;
			}
		}
		return null;
	}
	
}
