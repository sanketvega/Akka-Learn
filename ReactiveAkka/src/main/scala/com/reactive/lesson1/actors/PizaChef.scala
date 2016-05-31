package com.reactive.lesson1.actors

import akka.actor.Actor
import com.reactive.lesson1.messages._

final class PizaChef extends Actor {
  
  def receive() = {
    case MarinaraRequest => println("MarinaraRequest Found !")
    case MargheritaRequest => println("MargheritaRequest Found !")
  }
  
}