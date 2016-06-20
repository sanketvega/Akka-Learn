package com.reactive.lesson1

import akka.actor.ActorSystem
import akka.actor.Props
import com.reactive.lesson1.actors.supervisor.PizzaSupervisor
import com.reactive.lesson1.messages.MarinaraRequestWithPasta
import com.reactive.lesson1.messages.MargheritaRequest

object PizzaSupervisorApp {
  
  def main(ags: Array[String]): Unit = {
    val system = ActorSystem("PizzaSupervisorApp")

    val pizzaSupervisor = system.actorOf(Props[PizzaSupervisor], "PizzaSupervisor")

    pizzaSupervisor ! MarinaraRequestWithPasta

    pizzaSupervisor ! MargheritaRequest
  }
  
}