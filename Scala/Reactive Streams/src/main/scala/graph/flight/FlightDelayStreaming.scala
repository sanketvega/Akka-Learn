package graph.flight

import akka.stream.scaladsl.RunnableGraph
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.GraphDSL
import akka.stream.ClosedShape
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Flow
import akka.NotUsed
import akka.stream.Outlet
import akka.stream.FlowShape
import akka.stream.scaladsl.Broadcast
import akka.stream.UniformFanOutShape
import akka.stream.Inlet
import akka.stream.scaladsl.Sink

import scala.util.Try
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

final class FlightDelayStreaming(csvFile: String) {

  implicit val system = ActorSystem("FlightDelayStreaming")
  implicit val materializer = ActorMaterializer()

  def run(): RunnableGraph[NotUsed] = {
    val graph = RunnableGraph.fromGraph(GraphDSL.create() {
      implicit builder =>

        import GraphDSL.Implicits._

        //Reading from File. Iterator[String] is the source
        val A: Outlet[String] = builder.add(Source.fromIterator { () => flightReader }).out

        //Converting to FlightEvent, read the string and split the array to map the columns to FlightEvent class
        val B: FlowShape[String, FlightEvent] = builder.add(csvToFlightEvent)

        //Filter FlightEvent object which were delayed flight and map it to FlightDelayRecord
        val C: FlowShape[FlightEvent, FlightDelayRecord] = builder.add(filterToFlightDelayRecord)

        //Broadcast FlightDelayRecord (2)
        val D: UniformFanOutShape[FlightDelayRecord, FlightDelayRecord] = builder.add(Broadcast[FlightDelayRecord](2))

        //Take any one of the Broadcast and compute average delay and count for each flights
        val F: FlowShape[FlightDelayRecord, (String, Int, Int)] = builder.add(averageDelay)

        //Ignoring Another broadcast 
        val E: Inlet[Any] = builder.add(Sink.ignore).in

        //Printing the computed average delay
        val G: Inlet[Any] = builder.add(Sink.foreach { averageSink }).in

        /*
          This can be written as 
        	A.~>(B).~>(C).~>(D)
        	D.~>(E)
        	D.~>(F).~>(G)
        */

        A ~> B ~> C ~> D

        E <~ D

        G <~ F <~ D

        ClosedShape
    })

    graph
  }

  private val flightReader: Iterator[String] = scala.io.Source.fromFile(csvFile, "utf-8").getLines()

  private val csvToFlightEvent: Flow[String, FlightEvent, NotUsed] = Flow[String]
    .map { x => x.split(",").map { _.trim() } }
    .map { x => convertToFlightEvent(x) }

  private val filterToFlightDelayRecord: Flow[FlightEvent, FlightDelayRecord, NotUsed] = Flow[FlightEvent]
    .filter { x => Try(x.arrDelayMins.toInt).getOrElse(-1) > 0 }
    .mapAsyncUnordered(parallelism = 4) { x => //println(Thread.currentThread().getName)
      Future(FlightDelayRecord(x.year, x.month, x.dayOfMonth, x.flightNum, x.uniqueCarrier, x.arrDelayMins))
    }

  private val averageDelay: Flow[FlightDelayRecord, (String, Int, Int), NotUsed] = Flow[FlightDelayRecord]
    .groupBy(30, _.uniqueCarrier)
    .fold(("", 0, 0)) {
      (acc: (String, Int, Int), flightRecord: FlightDelayRecord) =>
        val count = acc._2 + 1
        val totalMinutes = acc._3 + Try(flightRecord.arrDelayMins.toInt).getOrElse(0)
        (flightRecord.uniqueCarrier, count, totalMinutes)
    }
    .mergeSubstreams

  private def convertToFlightEvent(cols: Array[String]): FlightEvent = {  
      FlightEvent(cols(0), cols(1), cols(2), cols(3), cols(4), cols(5), cols(6), cols(7), cols(8), cols(9), cols(10), cols(11), cols(12), cols(13), cols(14), cols(15), cols(16), cols(17), cols(18), cols(19), cols(20), cols(21), cols(22), cols(23), cols(24), cols(25), cols(26), cols(27), cols(28))
    }
  private def averageSink[A](a: A) {
    a match {
      case (a: String, b: Int, c: Int) => println(s"Delays for carrier ${a}: ${Try(c / b).getOrElse(0)} average mins, ${b} delayed flights")
      case x                           => println("no idea what " + x + "is!")
    }
  }
}

final case class FlightEvent(
  year: String,
  month: String,
  dayOfMonth: String,
  dayOfWeek: String,
  depTime: String,
  scheduledDepTime: String,
  arrTime: String,
  scheduledArrTime: String,
  uniqueCarrier: String,
  flightNum: String,
  tailNum: String,
  actualElapsedMins: String,
  crsElapsedMins: String,
  airMins: String,
  arrDelayMins: String,
  depDelayMins: String,
  originAirportCode: String,
  destinationAirportCode: String,
  distanceInMiles: String,
  taxiInTimeMins: String,
  taxiOutTimeMins: String,
  flightCancelled: String,
  cancellationCode: String, // (A = carrier, B = weather, C = NAS, D = security)
  diverted: String, // 1 = yes, 0 = no
  carrierDelayMins: String,
  weatherDelayMins: String,
  nasDelayMins: String,
  securityDelayMins: String,
  lateAircraftDelayMins: String)

final case class FlightDelayRecord(
    year: String,
    month: String,
    dayOfMonth: String,
    flightNum: String,
    uniqueCarrier: String,
    arrDelayMins: String) {

  override def toString = s"${year}/${month}/${dayOfMonth} - ${uniqueCarrier} ${flightNum} - ${arrDelayMins}"

}
  
  
  
 