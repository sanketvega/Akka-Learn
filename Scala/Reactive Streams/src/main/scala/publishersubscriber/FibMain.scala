package publishersubscriber

import akka.actor.ActorSystem
import akka.actor.Props
import akka.stream.actor.ActorPublisher
import java.math.BigInteger
import akka.stream.actor.ActorSubscriber
import publishersubscriber.fibonnaci.FibonacciSubscriber
import publishersubscriber.fibonnaci.DoublingFibonacciProcessor
import publishersubscriber.fibonnaci.FibonacciPublisher


object FibMain extends App {

  val system = ActorSystem("FibonnaciExample")
  doubleFibonacciProcessor()

  def fibonacciProcessor() {
    system.log.info("Starting Publisher")
    val publisherActor = system.actorOf(Props[FibonacciPublisher])
    val publisher = ActorPublisher[BigInteger](publisherActor)

    system.log.info("Starting Subscriber")
    val subscriberActor = system.actorOf(Props(new FibonacciSubscriber(500)))
    val subscriber = ActorSubscriber[BigInteger](subscriberActor)

    system.log.info("Subscribing to Publisher")
    publisher.subscribe(subscriber)
  }

  def doubleFibonacciProcessor() {
    system.log.info("Starting Publisher")
    val publisherActor = system.actorOf(Props[FibonacciPublisher])
    val publisher = ActorPublisher[BigInteger](publisherActor)

    system.log.info("Starting Doubling Processor")
    val doubleProcessorActor = system.actorOf(Props[DoublingFibonacciProcessor])
    val doublePublisher = ActorPublisher[BigInteger](doubleProcessorActor)
    val doubleSubscriber = ActorSubscriber[BigInteger](doubleProcessorActor)

    system.log.info("Starting Subscriber")
    val subscriberActor = system.actorOf(Props(new FibonacciSubscriber(500)))
    val subscriber = ActorSubscriber[BigInteger](subscriberActor)

    system.log.info("Subscribing to Processor to Publisher")
    publisher.subscribe(doubleSubscriber)
    system.log.info("Subscribing Subscriber to Processor")
    doublePublisher.subscribe(subscriber)
  }
}