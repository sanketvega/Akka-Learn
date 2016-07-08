package experiments

import akka.stream._
import akka.stream.actor.ActorPublisher
import akka.actor.ActorLogging
import akka.stream.actor.ActorSubscriber
import akka.stream.actor.WatermarkRequestStrategy
import akka.stream.scaladsl.Source
import akka.actor.ActorSystem
import akka.actor.Props
import akka.stream.scaladsl.Sink

import akka.stream.actor.ActorSubscriberMessage._
import akka.stream.actor.ActorPublisherMessage._
import akka.stream.actor.RequestStrategy
import java.util.Random
import java.util.concurrent.ThreadLocalRandom
import akka.actor.ActorRef

final case class Sequence(input: Array[Int]) extends AnyVal {

  override def toString = input.mkString(",")
}

class RandomSequenceProducer extends ActorPublisher[Sequence] with ActorLogging {

  def receive = {
    case size: Int =>      
      val sequence = Sequence((0 to size).toArray)
      if (isActive && totalDemand > 0) {
        log.info("Generating Sequence object for input : " + size)
        OnNext(sequence)
      }
    case Cancel =>
      log.info("Cancel Message Received ")
      context.stop(self)
  }

}

class RandomSequenceConsumer(actorRef: ActorRef) extends ActorSubscriber with ActorLogging {

  val requestStrategy = new RequestStrategy {
    override def requestDemand(remainingRequested: Int): Int = {
      Math.max(remainingRequested, 10)
    }
  }

  def receive = {
    case OnNext(sequence: Sequence) =>
      log.debug("Sequence Received : " + sequence)
      actorRef ! sequence
      
    case OnNext(value: Int) =>
      println(value)

    case OnComplete =>
      log.info("[RandomSequenceConsumer] RandomSequenceConsumer Stream Completed!")
      context.stop(self)

    case OnError(err: Exception) =>
      log.error(err, "[RandomSequenceConsumer] Receieved Exception in Fibonacci Stream")
      context.stop(self)
  }
}

class ThreadRunner(randomProducerActor: ActorRef) extends Thread {

  override def run() {
    println("Running......")
    var index = 0
    val min = 10
    val max = 500
    val random = new Random()
    while (index < 500) {
      randomProducerActor ! ThreadLocalRandom.current().nextInt(min, max + 1);
      index += 1
    }
  }

}

object ProducerSubscriber extends App {

  implicit val system = ActorSystem("ProducerSubscriber")
  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  val randomProducerActor = system.actorOf(Props[RandomSequenceProducer])

  val publisher = ActorPublisher[Sequence](randomProducerActor)

  val randomSubscriberActor = system.actorOf(Props(classOf[RandomSequenceConsumer], randomProducerActor))
  val subscriber = ActorSubscriber[Int](randomSubscriberActor)

  Source.fromPublisher(publisher)
        .map { x => x.input.reduce(_+_) }.async
        .runWith(Sink.fromSubscriber(subscriber))

  Thread.sleep(1000L)

  randomProducerActor ! 10

  /*val t1 = new ThreadRunner(randomProducerActor)
  val t2 = new ThreadRunner(randomProducerActor)
  val t3 = new ThreadRunner(randomProducerActor)

  t1.start()
  t2.start()
  t3.start()*/
}