package com.reactive.lesson1.actors.supervisor

import akka.actor.Actor
import akka.actor.ActorLogging
import com.reactive.lesson1.messages.PastaBadRequest
import com.reactive.lesson1.messages.PastaException
import com.reactive.lesson1.messages.PastaGoodRequest

class Pasta extends Actor with ActorLogging{
  
  def receive() = {
    case PastaBadRequest => 
      log.info("I got pasta request")
      throw new PastaException("Pasta is staled !")
    case PastaGoodRequest => log.info("Pasta is served !!")
  }
  
}