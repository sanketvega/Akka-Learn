package com.akka.stream.experiments.fibonnoci

import akka.stream.actor._
import akka.actor._
import java.math.BigInteger
import akka.stream.actor.ActorPublisherMessage._

class FibonacciPublisher extends ActorPublisher[BigInteger] with ActorLogging{
  
  var previous = BigInteger.ZERO
  var current = BigInteger.ZERO
  
  def receive = {
    case Request(cnt) => 
      log.debug("Request Received : "+cnt)
      sendFibs()
    case Cancel =>
      log.info("Cancel Received, Stopping Actor")
      context.stop(self)
    case _ =>
  }
  
  def sendFibs() {
    while(isActive && totalDemand > 0) {
      onNext(nextFib())
    }
  }
  
  def nextFib(): BigInteger = {
    if(current == BigInteger.ZERO){
      current = BigInteger.ONE
    }else{
      val tmp = previous.add(current)
      previous = current 
      current = tmp           
    }
    current
  }
  
}