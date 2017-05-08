package edu.cqu.wakaasst.core.domain.response;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.domain.item.PicDescItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "pic_desc_resp_msg")
public class PicDescResponseMessage extends BaseResponseMessage {

	@ManyToMany
	@JoinTable(name = "pic_desc_item")
	protected List<PicDescItemEntity> articles;
	
	public List<PicDescItemEntity> getArticles() {
		return articles;
	}

	public void setArticles(List<PicDescItemEntity> articles) {
		this.articles = articles;
	}
	
}
