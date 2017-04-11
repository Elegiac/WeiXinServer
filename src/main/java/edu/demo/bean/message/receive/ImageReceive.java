package edu.demo.bean.message.receive;

public class ImageReceive extends WeChatReceive {
	private String picUrl;
	private String mediaId;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toString() {
		return super.toString() + "ImageReceive [picUrl=" + picUrl + ", mediaId=" + mediaId + "]";
	}

}
