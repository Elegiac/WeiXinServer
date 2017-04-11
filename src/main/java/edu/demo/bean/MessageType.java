package edu.demo.bean;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
	TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), SHORTVIDEO("shortvideo"), LOCATION("location"), LINK(
			"link"), NEWS("news"), EVENT("event");

	private static final Map<String, MessageType> VALUE_TYPE_DIRECTORY = new HashMap<>();

	static {
		MessageType[] types = MessageType.values();
		for (MessageType type : types) {
			VALUE_TYPE_DIRECTORY.put(type.value, type);
		}
	}

	private String value;

	private MessageType(String value) {
		this.value = value;
	}

	public static MessageType fromValue(String value) {
		if (value == null) {
			throw new NullPointerException("message type value can not be null");
		}
		return VALUE_TYPE_DIRECTORY.get(value);
	}

	@Override
	public String toString() {
		return this.value;
	}
}
