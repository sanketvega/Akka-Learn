package com.reactive.lesson1

import akka.actor.ActorSystem
import akka.actor.Props
import com.reactive.lesson1.actors.PizzaLifeCycle
import com.reactive.lesson1.messages.MargheritaRequest
import com.reactive.lesson1.messages.PizzaException

object PizzaLifeCycleApp extends App{
  
  val actorSystem = ActorSystem("PizzaLifeCycle-App")
  val pizzaLifeCycleActor = actorSystem.actorOf(Props[PizzaLifeCycle], "PizzaLifeCycleActor")
  pizzaLifeCycleActor ! MargheritaRequest
  pizzaLifeCycleActor ! PizzaException
  pizzaLifeCycleActor ! MargheritaRequest
  
  //actorSystem.terminate()
  
}