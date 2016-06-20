package com.reactive.lesson1

import akka.actor.ActorSystem
import akka.actor.Props
import com.reactive.lesson1.actors.deadletters.PizzaDeadLetters
import com.reactive.lesson1.messages._
import com.reactive.lesson1.actors.deadletters.PizzaDeadLetterListener
import akka.actor.DeadLetter

object PizzaDeadLettersApp extends App{
  
  val system = ActorSystem("PizzaDeadLettersApp")
  
  val pizzaDeadLetterActor = system.actorOf(Props[PizzaDeadLetters], "PizzaDeadLetters")
  val pizzaDeadLetterListener = system.actorOf(Props[PizzaDeadLetterListener], "PizzaDeadLetterListener")
  
  system.eventStream.subscribe(pizzaDeadLetterListener, classOf[DeadLetter])
  
  pizzaDeadLetterActor ! MargheritaRequest
  pizzaDeadLetterActor ! StopPizzaBaking
  pizzaDeadLetterActor ! MarinaraRequest
  
  
}