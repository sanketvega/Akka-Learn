package com.reactive.lesson1.messages

final case object PizzaException extends Exception
final case class ToppingsException(message: String) extends Exception
final case class PastaException(message: String) extends Exception
