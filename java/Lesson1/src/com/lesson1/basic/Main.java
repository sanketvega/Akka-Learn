package com.lesson1.basic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class Main {

	public static void main(String[] args) {
		final ActorSystem actorSystem = ActorSystem.create();
		final TestActorRef<AkkaDB> akkaDbActorRef = TestActorRef.create(actorSystem, Props.create(AkkaDB.class));
		
		akkaDbActorRef.tell(new SetRequest("Kishore", "SW"), ActorRef.noSender());
		akkaDbActorRef.tell(new SetRequest("Aswin", "Service"), ActorRef.noSender());
		
		final AkkaDB akkaDB = akkaDbActorRef.underlyingActor();
		
		System.out.println(akkaDB.getMap().containsKey("Kishore"));
		actorSystem.terminate();
	}

}
