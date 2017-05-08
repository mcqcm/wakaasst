package edu.cqu.wakaasst.web.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

public class MyTextHandler implements MessageHandler{

	private static final Logger log = Logger.getLogger(PageController.class);
	
	public WeChatReqMsgType[] support() {
		return new WeChatReqMsgType[]{WeChatReqMsgType.TEXT};
	}

	public BaseResponseMessage handle(BaseRequestMessage msg, Map<String, Object> context) {
		
		TextRequestMessage message = (TextRequestMessage) msg;
		if(message.getContent().equals("冷笑话")){
			
        	try {
				Document doc = Jsoup.connect("http://www.jokeji.cn/").get();
				Element li = doc.select("div.newcontent.l_left>ul>li").first();
				String url = li.select("a").first().attr("href");
				
				doc = Jsoup.connect("http://www.jokeji.cn" + url).get();
				Elements ps = doc.select("span#text110>p");
				
	            PicDescResponseMessage resp = new PicDescResponseMessage();
	            resp.setCreatedDate(new Date());
	            resp.setMsgCreatedTime(new Date().getTime() / 1000);
	            resp.setFromUserName(msg.getToUserName());
	            resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.NEWS);
	            resp.setToUserName(msg.getFromUserName());
	            List<PicDescItemEntity> articles = new ArrayList<PicDescItemEntity>();
	            PicDescItemEntity article = new PicDescItemEntity();
	            article.setTitle("冷笑话");
	            article.setDescription("");
	            article.setPicUrl("http://c.hiphotos.baidu.com/album/w%3D2048/sign=aeacc6b3caef76093c0b9e9f1ae5a2cc/2cf5e0fe9925bc31fe38c3b65fdf8db1cb1370b3.jpg");
	            article.setUrl("http://www.jokeji.cn/");
            	articles.add(article);
	            for(int i=0; i < ps.size(); i++){
	            	li = ps.get(i);
	            	article = new PicDescItemEntity();
	            	article.setTitle(li.text());
	            	article.setDescription("");
	            	article.setPicUrl("");
	            	article.setUrl("http://www.jokeji.cn/");
	            	articles.add(article);
	            }
	            resp.setArticles(articles);
	            
	            return resp;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		} else if(message.getContent().equals("课表")){
			
			CommonService service = SystemUtils.getCommonService();
			User usr = service.findEntityByJPQL("select a from User a where a.openId='"+msg.getFromUserName()+"'", User.class);
			
			if(usr == null || usr.getStuSeria() == null){
				
				log.debug("没找到openId为"+msg.getFromUserName()+"的用户");
				
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
	            article.setUrl("http://202.202.68.101/wakaasst/pages/infobinding.do?openid="+msg.getFromUserName());
	        	articles.add(article);
	        	
	            resp.setArticles(articles);
	            
	            return resp;
			}
			
			log.debug(usr.getOpenId() + " " + usr.getStuSeria());
			
			int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
			week = week == 0 ? 7 : week;
			
		  	Document doc = null;
			try {
				doc = Jsoup.connect("http://graduate.cqu.edu.cn/mis/curricula/show_stu.jsp?stuSerial=" + usr.getStuSeria()).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(doc == null) {
				return null;
			}
			
			
			Element tbody = doc.select("table tbody").first();
	        String result = "";
	        for(int i = 2; i < tbody.children().size(); i++) {
	        	Element tr = tbody.child(i);
	        	Element td = tr.child(week);
	        	String tmp = td.text().replace("&nbsp;", " ");
	        	tmp = tmp.replace("<br>", "\n");
	        	tmp = tmp.replace("<br/>", "\n");
	        
	            if( StringUtils.isEmpty(tmp.trim()) ) {
	            	continue;
	            }

	            tmp = "\n[ "+ tr.child(0).text() + " ]\n" + (StringUtils.isEmpty(tmp.trim()) ? "无" : tmp);
	            result += tmp;
	        }
	        
	      	if( StringUtils.isEmpty(result.trim()) ) {
	        	result = "Happy啦！一整天没课！！";
	        }
	      	
			String[] map = new String[]{"一", "二", "三", "四", "五", "六", "日"};
			
			PicDescResponseMessage resp = new PicDescResponseMessage();
            resp.setCreatedDate(new Date());
            resp.setMsgCreatedTime(new Date().getTime() / 1000);
            resp.setFromUserName(msg.getToUserName());
            resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.NEWS);
            resp.setToUserName(msg.getFromUserName());
            List<PicDescItemEntity> articles = new ArrayList<PicDescItemEntity>();
            PicDescItemEntity article = new PicDescItemEntity();
            article.setTitle("星期"+map[week - 1]);
            article.setDescription(result);
            article.setPicUrl("");
            article.setUrl("http://graduate.cqu.edu.cn/mis/curricula/show_stu.jsp?stuSerial=" + usr.getStuSeria());
        	articles.add(article);
        	
            resp.setArticles(articles);
            
            return resp;

		}else if(message.getContent().equals("身份证号")){
			return handle2(message);
		}
		else if(message.getContent().equals("一卡通")){
			return handle3(message);
		}

		return null;
		
	}

	public Integer priority() {
		return -1;
	}
	
	public BaseResponseMessage handle2(TextRequestMessage msg){
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

		TextResponseMessage resp = new TextResponseMessage();
		resp.setCreatedDate(new Date());
		resp.setMsgCreatedTime(new Date().getTime() / 1000);
		resp.setFromUserName(msg.getToUserName());
		resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.TEXT);
		resp.setToUserName(msg.getFromUserName());
		resp.setContent("身份证号：" + usr.getIdentityid());
		return resp;
	}
	public BaseResponseMessage handle3(TextRequestMessage msg){
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
		String identityid=usr.getIdentityid();
		String result="";
		try {
			String username=getUsername(identityid);
			String password=identityid.substring(identityid.length()-6, identityid.length());
//***************************************************************************************************
			RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
			CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(globalConfig).build();
			
			HttpClientContext context = HttpClientContext.create();
			CookieStore cookieStore = new BasicCookieStore();
			context.setCookieStore(cookieStore);
			HttpGet request = new HttpGet();


				URIBuilder builder = new URIBuilder("http://ids.cqu.edu.cn/amserver/UI/Login?goto=http://i.cqu.edu.cn/ehome/index.do&goto=http://i.cqu.edu.cn&IDToken1="+username+"&IDToken2="+password);

				request.setURI(builder.build());

				HttpResponse response = client.execute(request, context);
				HttpEntity entity = response.getEntity();
				String postResult = EntityUtils.toString(entity);
				log.debug("postresult::::::"+postResult);
				if (entity != null) {
					EntityUtils.consume(entity);
				}
				if(postResult.indexOf("/amserver/UI/Login") != -1){
		        	return null;
		        }else{
						HttpGet httpget = new HttpGet();
						builder = new URIBuilder(
								"http://i.cqu.edu.cn/welcome/getUserInfo.do");
						httpget.setURI(builder.build());
						response = client.execute(httpget, context);
						entity = response.getEntity();
						postResult = EntityUtils.toString(entity);
						Document doc = Jsoup.parse(postResult);
						Elements links = doc.select("td[class=gradule-args]");
						log.debug("links::"+links.get(0).text());
						String money=links.get(0).text().replace("?", "");
						String books=links.get(1).text().replace("?", "");
						String debt=links.get(2).text().replace("?", "");
						result="一卡通余额："+money+";\n"+"借阅图书数："+books+";\n"+"超期违约金："+debt+";\n";
						log.debug("result::::::"+result);
						PicDescResponseMessage resp = new PicDescResponseMessage();
			            resp.setCreatedDate(new Date());
			            resp.setMsgCreatedTime(new Date().getTime() / 1000);
			            resp.setFromUserName(msg.getToUserName());
			            resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.NEWS);
			            resp.setToUserName(msg.getFromUserName());
			            List<PicDescItemEntity> articles = new ArrayList<PicDescItemEntity>();
			            PicDescItemEntity article = new PicDescItemEntity();
			            article.setTitle("一卡通信息");
			            article.setDescription(result);
			            article.setPicUrl("");
			            article.setUrl("http://ykt.cqu.edu.cn/cqeduportalHome.action");
			        	articles.add(article);
			        	
			            resp.setArticles(articles);
			            return resp;
		        }
//***************************************************************************************************		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
//		TextResponseMessage resp = new TextResponseMessage();
//		resp.setCreatedDate(new Date());
//		resp.setMsgCreatedTime(new Date().getTime() / 1000);
//		resp.setFromUserName(msg.getToUserName());
//		resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.TEXT);
//		resp.setToUserName(msg.getFromUserName());
//		resp.setContent("身份证号：" + usr.getIdentityid());
//		return resp;
	}
	private String getUsername(String number) throws IOException {  
        String username="";
        String url="http://i.cqu.edu.cn/iframe/searchStaffInfo.do?zjhm="+number;
        try {  
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();  
            Elements links = doc.select("table[class=staff-info]");   
            Element link=links.last();
            String text=link.text();
            String[] texts=text.split(" ");
            username=texts[texts.length-1];

        } catch (IOException e) {  
            e.printStackTrace();  
        }
  
        return username;  
    }  
}
