package edu.cqu.wakaasst.web.handlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.cqu.wakaasst.core.MessageHandler;
import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.WeChatReqMsgType;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.domain.item.PicDescItemEntity;
import edu.cqu.wakaasst.core.domain.request.TextRequestMessage;
import edu.cqu.wakaasst.core.domain.response.PicDescResponseMessage;
import edu.cqu.wakaasst.core.domain.response.TextResponseMessage;
import edu.cqu.wakaasst.domain.CommonService;
import edu.cqu.wakaasst.domain.SystemUtils;
import edu.cqu.wakaasst.domain.User;
import edu.cqu.wakaasst.web.controller.PageController;

public class MyTextHandler2 implements MessageHandler{

	private static final Logger log = Logger.getLogger(PageController.class);
	
	public WeChatReqMsgType[] support() {
		return new WeChatReqMsgType[]{WeChatReqMsgType.TEXT};
	}

	public BaseResponseMessage handle(BaseRequestMessage msg,
			Map<String, Object> context) {

		TextRequestMessage message = (TextRequestMessage) msg;
		if (message.getContent().equals("身份证号")) {

			CommonService service = SystemUtils.getCommonService();
			User usr = service.findEntityByJPQL(
					"select a from User a where a.openId='"
							+ msg.getFromUserName() + "'", User.class);

			if (usr == null || usr.getStuSeria() == null) {

				log.debug("没找到openId为" + msg.getFromUserName() + "的用户");

				PicDescResponseMessage resp = new PicDescResponseMessage();
				resp.setCreatedDate(new Date());
				resp.setMsgCreatedTime(new Date().getTime() / 1000);
				resp.setFromUserName(msg.getToUserName());
				resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.NEWS);
				resp.setToUserName(msg.getFromUserName());
				List<PicDescItemEntity> articles = new ArrayList<PicDescItemEntity>();
				PicDescItemEntity article = new PicDescItemEntity();
				article.setTitle("Error!");
				article.setDescription("Oops!我们发现您还没有关联学号，请猛戳此处");
				article.setPicUrl("http://202.202.68.101/wakaasst/img/404error.jpg");
				article.setUrl("http://202.202.68.101/wakaasst/pages/infobinding.do?openid="
						+ msg.getFromUserName());
				articles.add(article);

				resp.setArticles(articles);

				return resp;
			}

			RequestConfig globalConfig = RequestConfig.custom()
					.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
			CloseableHttpClient client = HttpClientBuilder.create()
					.setDefaultRequestConfig(globalConfig).build();

			HttpClientContext context2 = HttpClientContext.create();
			CookieStore cookieStore = new BasicCookieStore();
			context2.setCookieStore(cookieStore);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userId", usr.getUserId()));
			params.add(new BasicNameValuePair("password", usr.getPassword()));
			params.add(new BasicNameValuePair("userType", "student"));
			HttpEntity entity;
			try {
				entity = new UrlEncodedFormEntity(params, "gbk");

				HttpPost request = new HttpPost();
				request.setEntity(entity);
				URIBuilder builder = new URIBuilder(
						"http://graduate.cqu.edu.cn/mis/login.jsp");
				// if (params != null) {
				// for (Map.Entry<String, String> entry : params.entrySet()) {
				// builder.addParameter(entry.getKey(), entry.getValue());
				// }
				// }
				request.setURI(builder.build());

				HttpResponse response = client.execute(request, context2);
				entity = response.getEntity();
				String postResult = EntityUtils.toString(entity);

				if (entity != null) {
					EntityUtils.consume(entity);
				}

				if (postResult.indexOf("student.jsp") != -1) {
					HttpGet httpget = new HttpGet();
					builder = new URIBuilder(
							"http://graduate.cqu.edu.cn/mis/student/xueji/query/addbasic.jsp");
					httpget.setURI(builder.build());
					response = client.execute(httpget, context2);
					entity = response.getEntity();
					postResult = EntityUtils.toString(entity);
					log.debug("postResult:: " + postResult);
					Document doc = Jsoup.parse(postResult);
					Elements leftlinks = doc
							.select("td[class=mode4]");
					Elements rightlinks = doc
							.select("td[class=mode5]");
					log.debug("leftlink::"+leftlinks.text());
					for (int i = 0; i < leftlinks.size(); i++) {
						if (leftlinks.get(i).text().contains("身份证号")) {
							log.debug("身份证号："+rightlinks.get(i).text());
							String identityid = rightlinks.get(i).text();
							TextResponseMessage resp = new TextResponseMessage();
							resp.setCreatedDate(new Date());
							resp.setMsgCreatedTime(new Date().getTime() / 1000);
							resp.setFromUserName(msg.getToUserName());
							resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.TEXT);
							resp.setToUserName(msg.getFromUserName());
							resp.setContent("身份证号：" + identityid);
							return resp;
						}
					}
				}

			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
/*			
			log.debug(usr.getOpenId() + " " + usr.getStuSeria());
			String identityid="";
			if(usr.getIdentityid()==null||usr.equals("")){
						Map<String,String> miscookies=null;
//						Response res=null;
//						try {
//							res = Jsoup.connect("http://graduate.cqu.edu.cn/mis/curricula/show_stu.jsp?stuSerial=" + usr.getStuSeria()).execute();
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						miscookies=res.cookies();
//						
//						Iterator<String> it=miscookies.keySet().iterator();  
						Document doc = null;
						try {
							
//							Connection con=Jsoup.connect("http://graduate.cqu.edu.cn/mis/student/xueji/query/addbasic.jsp");
//							while(it.hasNext()){
//								String key=it.next();
//								log.debug( "cookies: " +key+"  " +miscookies.get(key));
//								con.cookie(key,miscookies.get(key));
//							}
//							doc=con.get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(doc == null) {
							return null;
						}
						
						
						Elements leftlinks = doc.select("td[align=right class=mode4]"); 
						Elements rightlinks = doc.select("td[align=left class=mode5]"); 
						for(int i=0;i<leftlinks.size();i++){
							if(leftlinks.get(i).text().substring(0, 4).equals("身份证号")){
								identityid=rightlinks.get(i).text();
							}
						}
			}else{
				identityid=usr.getIdentityid();
			}
	        
			
			PicDescResponseMessage resp = new PicDescResponseMessage();
            resp.setCreatedDate(new Date());
            resp.setMsgCreatedTime(new Date().getTime() / 1000);
            resp.setFromUserName(msg.getToUserName());
            resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.NEWS);
            resp.setToUserName(msg.getFromUserName());
            List<PicDescItemEntity> articles = new ArrayList<PicDescItemEntity>();
            PicDescItemEntity article = new PicDescItemEntity();
            article.setTitle("身份证号");
            article.setDescription(identityid);
            article.setPicUrl("");
            article.setUrl("http://graduate.cqu.edu.cn/mis/curricula/show_stu.jsp?stuSerial=" + usr.getStuSeria());
        	articles.add(article);
        	
            resp.setArticles(articles);
            
            return resp;
            */

		}

		return null;
		
	}

	public Integer priority() {
		return 1;
	}
	
}
