package edu.demo.bean.message.reply;

public class VideoReply extends WeChatReply {

	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return super.toString() + "VideoReply [video=" + video + "]";
	}

	public class Video {
		private String mediaId;
		private String title;
		private String description;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "Video [mediaId=" + mediaId + ", title=" + title + ", description=" + description + "]";
		}

	}
}
