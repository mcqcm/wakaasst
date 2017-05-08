package edu.cqu.wakaasst.core.domain.request;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseRequestMessage;
import edu.cqu.wakaasst.core.domain.item.VoiceItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "voice_req_msg")
public class VoiceRequestMessage extends BaseRequestMessage  {
	
	@ManyToOne
	@JoinColumn(name="voice_id")
	protected VoiceItemEntity voice;

	public VoiceItemEntity getVoice() {
		return voice;
	}

	public void setVoice(VoiceItemEntity voice) {
		this.voice = voice;
	}
	
}
