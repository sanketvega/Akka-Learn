package com.lesson1.basic;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public final class AkkaDB extends AbstractActor {

	private final LoggingAdapter LOGGER = Logging.getLogger(context().system(), this);
	private final Map<String, Object> map = new HashMap<>();

	public AkkaDB() {
		receive(ReceiveBuilder.match(SetRequest.class, message -> {
			LOGGER.info("Found key : " + message);
			map.put(message.getKey(), message.getValue());
		})
		.matchAny(message -> LOGGER.info("Unknown Message Found : "+message))

		.build());
	}

	public Map<String, Object> getMap() {
		return map;
	}
	
	

}
