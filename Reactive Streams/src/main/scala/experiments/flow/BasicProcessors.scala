package experiments.flow

import akka.stream.scaladsl.Flow
import akka.NotUsed

object BasicProcessors {
  
  def processA(): Flow[Long, Long, NotUsed] = {
    Flow[Long].map { x => x + 10 }
      .filter { x => x % 2 == 0 }
      .map { x => (x - 1) + (x + 2) }
  }
  
  def processB(): Flow[Long, Long, NotUsed] = {   
    Flow[Long].map { x => x % 3 }
      .map { x => x * x }
      .map { x => Math.abs(x) + 1 }
  }

  def processC(): Flow[Long, Long, NotUsed] = {
    Flow[Long].map { x => println(x);
      Math.log(x.toDouble).toLong
    }
      .reduce(_ + _)
  }
}