package com.lesson2.messages;

import java.io.Serializable;

public final class SetRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public final String key;
    public final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}
}
