package com.lesson2.client;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.lesson2.messages.GetRequest;
import com.lesson2.messages.SetRequest;

public final class ActorClient {
	
	private final ActorSystem actorSystem = ActorSystem.create("LocalSystem");
	
	private final LoggingAdapter LOGGER = Logging.getLogger(actorSystem, this);
	
	private final ActorSelection actorSelection;
	
	public ActorClient(final String address) {
		LOGGER.info("Inside Constructor");
		LOGGER.debug("Address : "+address);
		actorSelection = actorSystem.actorSelection("akka.tcp://akkademy@" + address + "/user/akkademy-db");
	}
	
	public CompletionStage<Object> set(final String key, final Object value){
		LOGGER.info("Inside set method");
		LOGGER.debug("Key : "+key+" Value : "+value);
		return toJava(ask(actorSelection, new SetRequest(key, value), 2000));
	}
	
	public CompletionStage<Object> get(final String key){
		LOGGER.info("Inside get method");
		LOGGER.debug("Key : "+key);
		return toJava(ask(actorSelection, new GetRequest(key), 20000));
	}
	
	public void terminate(){
		LOGGER.info("Terminating Actor ");
		actorSystem.terminate();
	}
	
}
