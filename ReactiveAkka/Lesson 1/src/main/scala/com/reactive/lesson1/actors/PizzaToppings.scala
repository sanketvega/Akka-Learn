package com.reactive.lesson1.actors

import akka.actor.Actor
import com.reactive.lesson1.messages.ExtraCheese
import com.reactive.lesson1.messages.Jalapeno

class PizzaToppings extends Actor{
  
  override def receive() = {
    case ExtraCheese => println("Aye, Extra cheese it is !!")
    case Jalapeno => println("It's Jalapeno !!")
  }
  
}