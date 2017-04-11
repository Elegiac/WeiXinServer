package edu.demo.bean.message.reply;

public class ImageReply extends WeChatReply {

	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return super.toString() + "ImageReply [image=" + image + "]";
	}

	public class Image {
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		@Override
		public String toString() {
			return "Image [mediaId=" + mediaId + "]";
		}

	}
}
