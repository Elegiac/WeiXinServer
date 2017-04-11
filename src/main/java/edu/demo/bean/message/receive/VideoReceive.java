package edu.demo.bean.message.receive;

public class VideoReceive extends WeChatReceive {
	private String mediaId;
	private String thumbMediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toString() {
		return super.toString() + "VideoReceive [mediaId=" + mediaId + ", thumbMediaId=" + thumbMediaId + "]";
	}

}
