package edu.demo.bean.message.receive;

public class TextReceive extends WeChatReceive {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return super.toString() + "TextReceive [content=" + content + "]";
	}

}
