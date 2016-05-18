package com.lesson2.messages;

import java.io.Serializable;

public final class GetRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final String key;
	
	public GetRequest(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
