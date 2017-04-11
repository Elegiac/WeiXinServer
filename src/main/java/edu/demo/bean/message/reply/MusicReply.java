package edu.demo.bean.message.reply;

public class MusicReply extends WeChatReply {

	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	@Override
	public String toString() {
		return super.toString() + "MusicReply [music=" + music + "]";
	}

	public class Music {
		private String title;
		private String description;
		private String musicUrl;
		private String HQMusicUrl;
		private String thumbMediaId;

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

		public String getMusicUrl() {
			return musicUrl;
		}

		public void setMusicUrl(String musicUrl) {
			this.musicUrl = musicUrl;
		}

		public String getHQMusicUrl() {
			return HQMusicUrl;
		}

		public void setHQMusicUrl(String hQMusicUrl) {
			HQMusicUrl = hQMusicUrl;
		}

		public String getThumbMediaId() {
			return thumbMediaId;
		}

		public void setThumbMediaId(String thumbMediaId) {
			this.thumbMediaId = thumbMediaId;
		}

		@Override
		public String toString() {
			return "Music [title=" + title + ", description=" + description + ", musicUrl=" + musicUrl + ", HQMusicUrl="
					+ HQMusicUrl + ", thumbMediaId=" + thumbMediaId + "]";
		}

	}
}
