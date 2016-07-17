package graph


import akka.stream.scaladsl.GraphDSL
import akka.stream.scaladsl.RunnableGraph
import akka.stream.ClosedShape
import akka.NotUsed
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Broadcast
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Merge
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object GraphExample extends App {

  implicit val system = ActorSystem("BasicGraphExamples")
  implicit val materializer = ActorMaterializer()
  
  val graph = simpleGraph()
  
  graph.run

  def simpleGraph(): RunnableGraph[NotUsed] = {

    val graph = RunnableGraph.fromGraph(GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        val A = builder.add(Source(1 to 10000)).out

        val B = builder.add(Broadcast[Int](2))

        val C1 = builder.add(Flow[Int].filter { x => x % 2 == 0 })

        val C2 = builder.add(Flow[Int].filterNot { x => x % 2 == 0 })

        val D = builder.add(Merge[Int](2))

        val E = builder.add(Flow[Int].map { x => x + 10 }.filter { x => x >= 10000 })

        val F = builder.add(Sink.foreach { println }).in

        A ~> B ~> C1 ~> D

        B ~> C2 ~> D

        D ~> E ~> F

        ClosedShape
    })

    graph
  }

}