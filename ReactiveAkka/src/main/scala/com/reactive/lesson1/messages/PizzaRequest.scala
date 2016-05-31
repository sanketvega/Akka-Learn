package com.reactive.lesson1.messages

sealed trait PizzaRequest

final case object MarinaraRequest extends PizzaRequest
final case object MargheritaRequest extends PizzaRequest

final case object ExtraCheese extends PizzaRequest
final case object Jalapeno extends PizzaRequest

