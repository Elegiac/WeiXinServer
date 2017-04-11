package edu.demo.bean.event;

public enum EventType {
	SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"), SCAN("SCAN"), LOCATION("LOCATION"), CLICK("CLICK"), VIEW(
			"VIEW");

	private String value;

	private EventType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
