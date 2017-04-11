package edu.demo.bean.message.reply;

import edu.demo.bean.message.WeChatMessage;

public abstract class WeChatReply extends WeChatMessage {

	public WeChatReply() {
		setMsgType(this.getClass().getSimpleName().replaceAll("Reply", "").toLowerCase());
	}
}
