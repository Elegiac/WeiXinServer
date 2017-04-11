package edu.demo.bean.message.reply;

public class VoiceReply extends WeChatReply {

	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	@Override
	public String toString() {
		return super.toString() + "VoiceReply [voice=" + voice + "]";
	}

	public class Voice {
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		@Override
		public String toString() {
			return "Voice [mediaId=" + mediaId + "]";
		}

	}
}
