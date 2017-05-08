package edu.cqu.wakaasst.core.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.WakaAsstException;
import edu.cqu.wakaasst.core.domain.auth.Authentication;
import edu.cqu.wakaasst.core.util.AppUtil;

@Service
public class AuthService {
	
	private static final Logger log = Logger.getLogger(AuthService.class);

	@Autowired
	protected WakaAsstConfig config;

	public Authentication getAccessToken(String appid, String appsecret) throws WakaAsstException {
		Map<String, String> paramsJson = new HashMap<String, String>();
		paramsJson.put("grant_type", "client_credential");
		paramsJson.put("appid", appid);
		paramsJson.put("secret", appsecret);

		Authentication result = AppUtil.sendRequest(config.getAccessTokenCreateUrl(), HttpMethod.GET, paramsJson, null, Authentication.class);
		result.setGrantType("client_credential");
		result.setAppid(appid);
		result.setSecret(appsecret);
		return result;
	}

	public boolean checkSignature(String signature, String timestamp, String nonce, String echostr) {
		
		String excepted = encrypt(getStrForEncryption(timestamp, nonce, config.getToken()));

		if (signature == null || !signature.equals(excepted)) {
			log.error("Authentication failed! excepted echostr ->" + excepted);
			log.error("                                 actual ->" + signature);
			return false;
		}

		return true;
	}

	protected static String getStrForEncryption(String timestamp, String nonce, String token) {
		List<String> list = new ArrayList<String>();
		list.add(timestamp);
		list.add(nonce);
		list.add(token);

		StringBuffer sb = new StringBuffer();
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	protected static String encrypt(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] b = md.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// never happens
		}
		return null;
	}
	
}

