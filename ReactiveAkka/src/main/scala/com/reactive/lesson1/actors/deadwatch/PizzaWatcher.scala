package com.reactive.lesson1.actors.deadwatch

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import akka.actor.PoisonPill
import akka.actor.Terminated

class PizzaWatcher extends Actor with ActorLogging{
  
  val pizzaActor = context.actorOf(Props[PizzaActor], "PizzaActor")
  context.watch(pizzaActor)
  
  def receive() = {
    case "Stop" => pizzaActor ! PoisonPill
    case Terminated(actorRef) => log.error(s"Actor ${actorRef} terminated")
  }
  
}