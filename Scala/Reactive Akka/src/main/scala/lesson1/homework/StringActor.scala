package lesson1.homework

import java.util

import akka.actor.Actor

/**
  * Created by kkishore on 6/28/16.
  */
class StringActor extends Actor{

  val list: util.List[LastString] = new util.ArrayList[LastString](1)

  def receive: PartialFunction[Any, Unit] = {
    case value: String =>
      if(list.isEmpty) {
        list.add(new LastString(value))
      } else {
        list.clear()
        list.add(new LastString(value))
      }

    case GetLastValue => if(list.isEmpty) sender() ! "" else sender() ! list.get(0)
  }

  def getLastStringValue() = if(list.isEmpty) "" else list.get(0)

}
