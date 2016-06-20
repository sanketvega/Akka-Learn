package com.reactive.lesson1.actors.supervisor

import akka.actor.Actor
import akka.actor.ActorLogging
import com.reactive.lesson1.messages._
import akka.actor.ActorSystem
import akka.actor.Props

class PizzaToppings extends Actor with ActorLogging {

  def receive() = {
    case ExtraCheese => log.info("ExtraCheese it is")
    case Jalapeno    => log.info("More Jalapeno")
    case ToppingsBadRequest => {
      log.info("Bad toppings will not be served")
      throw new ToppingsException("Bad toppings will not be served")
    }
  }

}