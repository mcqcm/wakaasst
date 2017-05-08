package edu.cqu.wakaasst.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cqu.wakaasst.core.WakaAsstException;
import edu.cqu.wakaasst.domain.CommonService;
import edu.cqu.wakaasst.domain.SystemUtils;
import edu.cqu.wakaasst.domain.User;

@Controller
@RequestMapping("/pages/infobinding")
public class PageController {
	private static final Logger log = Logger.getLogger(PageController.class);
	
	@RequestMapping(method = {RequestMethod.GET,})
	public String get(@RequestParam("openid") String openId, Model model) {
		model.addAttribute("openid", openId);
		return "/pages/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam("stunum") String stunum, 
			@RequestParam("password") String password,
			@RequestParam("openid") String openId) throws UnsupportedEncodingException, WakaAsstException {        
        
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(globalConfig).build();
		
		HttpClientContext context = HttpClientContext.create();
		CookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userId", stunum));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("userType", "student"));
        HttpEntity entity = new UrlEncodedFormEntity(params, "gbk");
		HttpPost request = new HttpPost();
		request.setEntity(entity);
		
		try {

			URIBuilder builder = new URIBuilder("http://graduate.cqu.edu.cn/mis/login.jsp");
//			if (params != null) {
//				for (Map.Entry<String, String> entry : params.entrySet()) {
//					builder.addParameter(entry.getKey(), entry.getValue());
//				}
//			}
			request.setURI(builder.build());

			HttpResponse response = client.execute(request, context);
			entity = response.getEntity();
			String postResult = EntityUtils.toString(entity);
			
			if (entity != null) {
				EntityUtils.consume(entity);
			}
			
			if(postResult.indexOf("wrongpwd.jsp") != -1){
	        	return "/pages/tables";
	        }
			
			 if(postResult.indexOf("student.jsp") != -1){
				HttpGet httpget = new HttpGet();
				builder = new URIBuilder("http://graduate.cqu.edu.cn/mis/student_manage.jsp");
				httpget.setURI(builder.build());
				response = client.execute(httpget, context);
				entity = response.getEntity();
				postResult = EntityUtils.toString(entity);
				if (entity != null) {
					EntityUtils.consume(entity);
				}
				
				Pattern pattern = Pattern.compile("stuSerial=\\d+");
				Matcher matcher = pattern.matcher(postResult);
				String stuSerial = null;
				
				if(matcher.find()){
					builder = new URIBuilder(
							"http://graduate.cqu.edu.cn/mis/student/xueji/query/addbasic.jsp");
					httpget.setURI(builder.build());
					response = client.execute(httpget, context);
					entity = response.getEntity();
					postResult = EntityUtils.toString(entity);
					Document doc = Jsoup.parse(postResult);
					Elements table = doc.select("table#tab1 table");
					Elements trs=table.select("tr");
					String identityid= trs.get(6).child(1).text();
					stuSerial = matcher.group().substring(10);
					
					CommonService service = SystemUtils.getCommonService();
					User usr = new User();
					usr.setOpenId(openId);
					usr.setUserId(stunum);
					usr.setPassword(password);
					usr.setStuSeria(stuSerial);
					usr.setIdentityid(identityid.substring(0, identityid.length()-1));
					service.addEntity(usr);
				}
				

				
				if(stuSerial == null){
					return "/pages/tables";
				} else {
					
					log.error(stuSerial);
					
					
					return "/pages/index";
				}
			
			}
			
			return "/pages/tables";
			
		} catch (IOException e) {
			throw new WakaAsstException(e);
		} catch (URISyntaxException e) {
			throw new WakaAsstException(e);
		}

    }
}
