package lesson1.db

import java.util

import akka.actor.Actor
import akka.event.Logging
import lesson1.db.messages.Messages

/**
  * Created by kkishore on 6/22/16.
  */
class AkkademyDb extends Actor {

  val map = new util.HashMap[String, Object]()
  val logger = Logging(context.system, this)

  override def receive(): PartialFunction[Any, Unit] = {
    case Messages.SetRequest(key, value) =>
      logger.info(s"Key : {$key} Value : {$value.toString}")
      map.put(key, value)

    case other => logger.error(s"Unknown Message Found : $other")
  }

}
