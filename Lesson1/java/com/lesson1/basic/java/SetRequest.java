package com.lesson1.basic.java;

public final class SetRequest {

	private final String key;
	private final String value;

	public SetRequest(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public String toString(){
		return "[ Key : "+key+", Value : "+value+" ]";
	}

}
