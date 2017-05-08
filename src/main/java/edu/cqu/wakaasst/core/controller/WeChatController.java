package edu.cqu.wakaasst.core.controller;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cqu.wakaasst.core.WakaAsstException;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.service.AuthService;
import edu.cqu.wakaasst.core.service.MessageService;

@Controller
@RequestMapping("/wechat")
public class WeChatController {
	private static final Logger log = Logger.getLogger(WeChatController.class);
	
	@Autowired
	private AuthService authService;
	@Autowired
	private MessageService messageService;
	
	@ResponseBody
	@RequestMapping(method = {RequestMethod.GET,})
	public String checkSignature(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) throws WakaAsstException {
		if (authService.checkSignature(signature, timestamp, nonce, echostr)) {
			log.info("received authentication message from wechat server.");
			return echostr;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestBody String requestBody) throws DocumentException, WakaAsstException {
		BaseRequestMessage msg = messageService.parseXML(requestBody);
		log.info("received " + msg.getMsgType() + " message.");
		
		BaseResponseMessage resp = messageService.handleReqMsg(msg);
		
		return messageService.genXmlForRespMsg(resp).asXML();
	}

}
