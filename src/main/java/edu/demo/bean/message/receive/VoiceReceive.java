package edu.demo.bean.message.receive;

public class VoiceReceive extends WeChatReceive {
	private String mediaId;
	private String format;
	private String recognition;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	@Override
	public String toString() {
		return super.toString() + "VoiceReceive [mediaId=" + mediaId + ", format=" + format + ", recognition="
				+ recognition + "]";
	}

}
