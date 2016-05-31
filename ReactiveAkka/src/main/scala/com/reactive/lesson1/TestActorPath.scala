package com.reactive.lesson1

import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props
import com.reactive.lesson1.actors.PizzaSupervisor
import com.reactive.lesson1.messages.MarinaraRequest
import com.reactive.lesson1.messages.MargheritaRequest
import com.reactive.lesson1.messages.PizzaException

object TestActorPath extends App{
  
  val actorSystem = ActorSystem("Pizza")
  
  val pizzaSupervisor: ActorRef = actorSystem.actorOf(Props[PizzaSupervisor], "PizzaSupervisor")
  println(pizzaSupervisor.path)
  
  pizzaSupervisor ! MarinaraRequest
  
  actorSystem.shutdown()
}