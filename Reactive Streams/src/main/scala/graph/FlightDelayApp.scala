package graph

import graph.flight.FlightDelayStreaming

object FlightDelayApp extends App {

  val flightDelayStreaming = new FlightDelayStreaming("/home/kkishore/Documents/DataSet/2008.csv")
  val graph = flightDelayStreaming.run()
  implicit val materializer = flightDelayStreaming.materializer
  graph.run()
}