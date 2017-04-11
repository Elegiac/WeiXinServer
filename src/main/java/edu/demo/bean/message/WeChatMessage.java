package edu.demo.bean.message;

import edu.demo.bean.MessageType;

public abstract class WeChatMessage {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private MessageType msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = MessageType.fromValue(msgType);
	}

	@Override
	public String toString() {
		return "WeChatMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + "]";
	}

}
