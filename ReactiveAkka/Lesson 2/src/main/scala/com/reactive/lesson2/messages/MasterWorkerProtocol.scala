package com.reactive.lesson2.messages

object MasterWorkerProtocol {
  
  case class RegisterWorker(workerId: String)
  case class WorkerRequestWork(workerId: String)
  case class WorkIsDone(workerId: String, workId: String, result: Any)
  case class WorkFailed(workerId: String, workId: String)
  
  case object WorkIsReady
  case class Ack(id: String)
}