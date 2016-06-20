package com.reactive.lesson1.actors.deadletters

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.DeadLetter

class PizzaDeadLetterListener extends Actor with ActorLogging{
  
  def receive() = {
    case deadLetters: DeadLetter => log.info(s"Unprocessed dead letters in pizza request $deadLetters")
  }
  
}