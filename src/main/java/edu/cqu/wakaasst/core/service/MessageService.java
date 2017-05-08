package edu.cqu.wakaasst.core.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import edu.cqu.wakaasst.core.MessageHandler;
import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.WakaAsstException;
import edu.cqu.wakaasst.core.WeChatReqMsgType;
import edu.cqu.wakaasst.core.WeChatRespMsgType;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.domain.response.ImageResponseMessage;
import edu.cqu.wakaasst.core.domain.response.MusicResponseMessage;
import edu.cqu.wakaasst.core.domain.response.PicDescResponseMessage;
import edu.cqu.wakaasst.core.domain.response.TextResponseMessage;
import edu.cqu.wakaasst.core.domain.response.VideoResponseMessage;
import edu.cqu.wakaasst.core.domain.response.VoiceResponseMessage;

@Service
public class MessageService {
	
	private static final Logger log = Logger.getLogger(MessageService.class);
	
	protected static Map<WeChatReqMsgType, TreeSet<MessageHandler>> handlers;
	
	static {
		handlers = new HashMap<WeChatReqMsgType, TreeSet<MessageHandler>>();
		loadHandles();
	}
	
	public BaseRequestMessage parseXML(String xml) throws DocumentException, WakaAsstException {
		Element ele = DocumentHelper.parseText(xml).getRootElement();
		String msgType = null;
		if ((msgType = ele.elementText("MsgType")) == null) {
			throw new WakaAsstException("cannot find MsgType Node!\n" + xml);
		}
		
		WeChatReqMsgType msgTypeEnum = WeChatReqMsgType.inst(msgType);
		switch (msgTypeEnum) {
		case EVENT:
			return MessageHelper.parseEventReqMsg(ele);
		case IMAGE:
			return MessageHelper.parseImageReqMsg(ele);
		case LINK:
			return MessageHelper.parseLinkReqMsg(ele);
		case LOCATION:
			return MessageHelper.parseLocationReqMsg(ele);
		case TEXT:
			return MessageHelper.parseTextReqMsg(ele);
		case VIDEO:
			return MessageHelper.parseVideoReqMsg(ele);
		case VOICE:
			return MessageHelper.parseVoiceReqMsg(ele);
		default:
			// never happens
			break;
		}
		return null;
	}
	
	public BaseResponseMessage handleReqMsg(BaseRequestMessage msg) {
		TreeSet<MessageHandler> handlerList = handlers.get(WeChatReqMsgType.inst(msg.getMsgType()));
		
		if(handlerList == null) {
			return defaultResult(msg.getToUserName(), msg.getFromUserName());
		}
		
		Map<String, Object> context = new HashMap<String, Object>();
		BaseResponseMessage result = null;
		for (MessageHandler handler : handlerList) {
			result = handler.handle(msg, context);
		}
		
		if (result == null) {
			result = defaultResult(msg.getToUserName(), msg.getFromUserName());
		}
		return result;
	}
	
	public Element genXmlForRespMsg(BaseResponseMessage resp) throws DocumentException {
		WeChatRespMsgType type = WeChatRespMsgType.inst(resp.getMsgType());
		switch (type) {
		case IMAGE:
			return MessageHelper.doGenXmlForRespMsg((ImageResponseMessage) resp);
		case MUSIC:
			return MessageHelper.doGenXmlForRespMsg((MusicResponseMessage) resp, ((MusicResponseMessage) resp).getThumb());
		case NEWS:
			return MessageHelper.doGenXmlForRespMsg((PicDescResponseMessage) resp);
		case TEXT:
			return MessageHelper.doGenXmlForRespMsg((TextResponseMessage) resp);
		case VIDEO:
			return MessageHelper.doGenXmlForRespMsg((VideoResponseMessage) resp);
		case VOICE:
			return MessageHelper.doGenXmlForRespMsg((VoiceResponseMessage) resp);
		default:
			break;
		}
		return null;
	}
	
	protected TextResponseMessage defaultResult(String fromUserName, String toUserName) {
		TextResponseMessage result = new TextResponseMessage();
		result.setContent("您好,您的消息已收到.");
		result.setCreatedDate(new Date());
		result.setMsgCreatedTime(new Date().getTime() / 1000);
		result.setFromUserName(fromUserName);
		result.setMsgType(WakaAsstConfig.WeChatRespMsgType.TEXT);
		result.setToUserName(toUserName);
		return result;
	}
	
	private static void loadHandles() {
		try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(MessageService.class.getResourceAsStream("/wakaasst-handlers.xml"));
            Element list = document.getRootElement();
            @SuppressWarnings("unchecked")
			List<Element> elements = list.elements();
            for (Element element : elements) {
                if (element.getName().equals("bean")) {
                    Attribute className = element.attribute("class");
                    log.debug(className.getStringValue());
                    
                    MessageHandler handler = loadClass(className.getStringValue());
                    WeChatReqMsgType[] types = handler.support();
                    for(WeChatReqMsgType type : types){
                    	if(!handlers.containsKey(type)){
                    		handlers.put(type, new TreeSet<MessageHandler>(new MessageHandlerComparator()));
                    	}
                    	handlers.get(type).add(handler);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private static MessageHandler loadClass(String className) {
        try {
            Class<?> ins = Class.forName(className);
            return (MessageHandler) ins.newInstance();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

}

class MessageHandlerComparator implements Comparator<MessageHandler> {

	public int compare(MessageHandler o1, MessageHandler o2) {
		if (o1.priority() < o2.priority()) {
			return -1;
		} else if (o1.priority() > o2.priority()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
