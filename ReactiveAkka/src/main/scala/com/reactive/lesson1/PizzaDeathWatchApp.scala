package com.reactive.lesson1

import akka.actor.ActorSystem
import com.reactive.lesson1.actors.deadwatch.PizzaActor
import akka.actor.Props
import com.reactive.lesson1.actors.deadwatch.PizzaWatcher
import com.reactive.lesson1.messages.MargheritaRequest

object PizzaDeathWatchApp extends App{
  
  val system = ActorSystem("PizzaDeathWatchApp")
  
  val pizzaActor = system.actorOf(Props[PizzaActor], "PizzaActor")
  
  val pizzaDeathWatch = system.actorOf(Props[PizzaWatcher], "PizzaWatcher")
  
  pizzaActor ! MargheritaRequest
  pizzaDeathWatch ! "Stop"
  
}