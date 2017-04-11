package edu.demo.bean.message.reply;

import java.util.List;

public class NewsReply extends WeChatReply {
	private String articleCount;
	private List<Item> articles;

	public String getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}

	public List<Item> getArticles() {
		return articles;
	}

	public void setArticles(List<Item> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return super.toString() + "NewsReply [articleCount=" + articleCount + ", articles=" + articles + "]";
	}

	public class Item {
		private String title;
		private String description;
		private String picUrl;
		private String url;

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

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return "Item [title=" + title + ", description=" + description + ", picUrl=" + picUrl + ", url=" + url
					+ "]";
		}

	}
}
