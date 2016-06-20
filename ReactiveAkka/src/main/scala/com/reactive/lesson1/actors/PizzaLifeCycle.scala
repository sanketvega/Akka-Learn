package com.reactive.lesson1.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import com.reactive.lesson1.messages.MargheritaRequest
import com.reactive.lesson1.messages.MarinaraRequest
import com.reactive.lesson1.messages.PizzaException

class PizzaLifeCycle extends Actor with ActorLogging {

  override def preStart() = {
    log.info("Inside preStart method")
    log.info("Pizza request received")
  }

  def receive = {
    case MargheritaRequest => log.info("MargheritaRequest Found")
    case MarinaraRequest   => log.info("MarinaraRequest Found")
    case PizzaException    => throw new Exception("Pizza Fried")
    case _                 => log.info("Unkown request")
  }
  
  override def preRestart(throwable: Throwable, message: Option[Any]) = {
    log.info("Pizza Baking stopped because : "+throwable.getMessage)
    postStop()
  }
  
  override def postRestart(throwable: Throwable) = {
    log.info("New Pizza process started because earlier " +throwable.getMessage)
    preStart()
  }
  
  override def postStop() = {
    log.info("Pizza request has been served")
  }

}