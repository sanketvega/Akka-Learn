package com.reactive.lesson1.actors.deadletters

import akka.actor.Actor
import akka.actor.ActorLogging
import com.reactive.lesson1.messages._

class PizzaDeadLetters extends Actor with ActorLogging {

  override def preStart() = {
    log.info("Inside preStart !!")
    log.info("Calling Receive method")
  }

  def receive() = {
    case MarinaraRequest => log.info("MarinaraRequest Received")
    case MargheritaRequest => log.info("MargheritaRequest Received")
    case PizzaException => throw new Exception("Pizza Exception")
    case StopPizzaBaking => context.stop(self)
  }
  
  override def preRestart(throwable: Throwable, message: Option[Any]) = {
    log.info("Pizza baking restarted because "+throwable.getMessage)
    postStop()
  }
  
  override def postRestart(throwable: Throwable) = {
    log.info("New Pizza process started because "+ throwable.getMessage)
    preStart()
  }
  
  override def postStop() = {
    log.info("Pizza Request is done")
  }

}