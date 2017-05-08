package edu.cqu.wakaasst.scheduler;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(60 * 1000);

	public void process(Page page) {
		Map<String, String> cookies = site.getCookies();
		if(cookies.isEmpty()){
			System.out.println("empty");
		}
		for(String key : cookies.keySet()){
			System.out.println(key + " : " + cookies.get(key));
		}
		
		int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		week = week == 0 ? 7 : week;
		Document doc = page.getHtml().getDocument();
		Element tbody = doc.select("table tbody").first();
        String result = "";
        for(int i = 2; i < tbody.children().size(); i++) {
        	Element tr = tbody.child(i);
        	Element td = tr.child(week - 1);
        	
        	String tmp = td.html().replace("&nbsp;", "");
        	tmp = tmp.replaceAll("<br\\s/>", "\n");
        	
        
            if( StringUtils.isEmpty(tmp.trim()) ) {
            	continue;
            }

            tmp = "\n[ "+ tr.child(0).text() + " ]\n" + (StringUtils.isEmpty(tmp.trim()) ? "无" : tmp);
            result += tmp;
        }
        System.out.println(result);
	}

	public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	
        Spider.create(new GithubRepoPageProcessor())
                .addUrl("http://graduate.cqu.edu.cn/mis/curricula/show_stu.jsp?stuSerial=302767")
                .thread(1)
                .run();
        
    }

}
