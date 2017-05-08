package edu.cqu.wakaasst.core.domain.response;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.cqu.wakaasst.core.WakaAsstConfig;
import edu.cqu.wakaasst.core.domain.base.BaseResponseMessage;
import edu.cqu.wakaasst.core.domain.item.VoiceItemEntity;

@Entity
@Table(name = WakaAsstConfig.TABLE_PREFIX + "voice_resp_msg")
public class VoiceResponseMessage extends BaseResponseMessage {

	@ManyToOne
	@JoinColumn(name = "voice_id")
	protected VoiceItemEntity voice;

	public VoiceItemEntity getVoice() {
		return voice;
	}

	public void setVoice(VoiceItemEntity voice) {
		this.voice = voice;
	}
	
}
