package com.lesson1.basic.scala

import akka.actor.Actor
import akka.event.Logging
import java.util.HashMap

class AkkaDB extends Actor {
  
  val log = Logging.getLogger(context.system, this)
  
  val map = new HashMap[String, Object]()
  
  override def receive() = {
    case SetRequest(key, value) => {
      log.info("SetRequest")
      log.debug("Key : "+key+" value : "+value)
      map.put(key, value)      
    }
    case _ => log.error("Invalid message : ")
  }
  
}