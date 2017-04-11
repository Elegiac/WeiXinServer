package edu.demo.bean.event;

import edu.demo.bean.message.WeChatMessage;

public class WeChatEvent extends WeChatMessage {
	private EventType event;

	public EventType getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = EventType.valueOf(event.toUpperCase());
	}

	@Override
	public String toString() {
		return super.toString() + "WeChatEvent [event=" + event + "]";
	}

}
