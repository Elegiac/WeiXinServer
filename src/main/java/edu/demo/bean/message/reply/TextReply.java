package edu.demo.bean.message.reply;

public class TextReply extends WeChatReply {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return super.toString() + "TextReply [content=" + content + "]";
	}

}
