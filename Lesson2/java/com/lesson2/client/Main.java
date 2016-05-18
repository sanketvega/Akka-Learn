package com.lesson2.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		final ActorClient actorClient = new ActorClient("127.0.0.1:2552");
		actorClient.set("Kris", 27);
		
		final CompletableFuture<Object> result = actorClient.get("Kris").toCompletableFuture();
		
		final int age = (int) result.get();
		
		System.out.println(age);
		
		
		
	}

}
