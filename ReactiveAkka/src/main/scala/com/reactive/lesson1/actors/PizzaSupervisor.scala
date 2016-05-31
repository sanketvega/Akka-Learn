package com.reactive.lesson1.actors

import akka.actor.Actor
import akka.actor.Props
import com.reactive.lesson1.messages.MarinaraRequest
import com.reactive.lesson1.messages.ExtraCheese
import com.reactive.lesson1.messages.MargheritaRequest
import com.reactive.lesson1.messages.PizzaException

class PizzaSupervisor extends Actor{
  
  val pizzaTopings = context.actorOf(Props[PizzaToppings], "PizzaToppings")
  
  def receive() = {
    case MarinaraRequest => {
      println("Cheese with MarinaraRequest !!")
      println(pizzaTopings.path)
      pizzaTopings ! ExtraCheese
    }
    
    case MargheritaRequest => println("I have MargheritaRequest !!")
    
    case PizzaException => println("Pizza Fried !!!")
  }
  
}