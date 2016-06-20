package com.reactive.lesson1

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props
import com.reactive.lesson1.actors._
import com.reactive.lesson1.messages._

object PizzaCustomer extends App{
  
  val actorSystem = ActorSystem("Pizza")
  val actorChef: ActorRef = actorSystem.actorOf(Props[PizaChef])
  actorChef ! MargheritaRequest
  actorChef ! MargheritaRequest
  actorSystem.terminate()
}