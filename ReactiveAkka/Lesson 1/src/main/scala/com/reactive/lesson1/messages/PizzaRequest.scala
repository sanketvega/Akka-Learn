package com.reactive.lesson1.messages

sealed trait PizzaRequest

final case object MarinaraRequest extends PizzaRequest
final case object MargheritaRequest extends PizzaRequest
final case object StopPizzaBaking extends PizzaRequest

final case object ExtraCheese extends PizzaRequest
final case object Jalapeno extends PizzaRequest

final case object PastaGoodRequest extends PizzaRequest
final case object PastaBadRequest extends PizzaRequest
final case object PizzaBadRequest extends PizzaRequest
final case object ToppingsBadRequest extends PizzaRequest

final case object MarinaraRequestWithPasta extends PizzaRequest
