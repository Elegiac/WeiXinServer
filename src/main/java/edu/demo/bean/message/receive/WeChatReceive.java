package edu.demo.bean.message.receive;

import edu.demo.bean.message.WeChatMessage;

public abstract class WeChatReceive extends WeChatMessage {
	private String msgId;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Override
	public String toString() {
		return super.toString() + "WeChatReceive [msgId=" + msgId + "]";
	}

}
