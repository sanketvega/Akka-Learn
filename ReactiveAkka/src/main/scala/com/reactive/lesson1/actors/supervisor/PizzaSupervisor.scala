package com.reactive.lesson1.actors.supervisor

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import com.reactive.lesson1.messages._
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import akka.actor.AllForOneStrategy

class PizzaSupervisor extends Actor with ActorLogging {

  val pizzaToppings = context.actorOf(Props[PizzaToppings], "PizzaToppings")
  val pasta = context.actorOf(Props[Pasta], "Pasta")

  def receive() = {
    case MarinaraRequestWithPasta =>
      log.info("I have MarinaraRequestWithPasta with extra cheese")
      pizzaToppings ! ExtraCheese
      pasta ! PastaBadRequest
      pasta ! PastaGoodRequest

    case MargheritaRequest =>
      log.info("I have MargheritaRequest")
      pizzaToppings ! ToppingsBadRequest

    case PizzaException => throw new Exception("Pizza Fried")
  }

  /*override val supervisorStrategy = OneForOneStrategy() {
    case _: PastaException =>
      log.error(s"Restarting actor")
      Stop
    case _: ToppingsException =>
      log.error(s"Toppings error")
      Restart
  }*/
  
  override val supervisorStrategy = AllForOneStrategy() {
    case _: PastaException =>
      log.error(s"Restarting Pasta actor")
      Stop
    case _: ToppingsException =>
      log.error(s"Restarting Toppings Actor")
      Restart
  }

}