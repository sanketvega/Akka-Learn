package lesson1.db.test

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import lesson1.db.AkkademyDb
import lesson1.db.messages.Messages.SetRequest
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}

/**
  * Created by kkishore on 6/22/16.
  */
class AkkademyDbTest extends FunSpecLike with Matchers with BeforeAndAfterEach {

  implicit val actorSystem = ActorSystem.create()

  describe("akkaddemydb") {
    describe("SetRequest Test") {
      it("Place Key/Value into map") {
        val actorRef = TestActorRef(new AkkademyDb())
        actorRef ! SetRequest("Name", "K. Kishore")
        val akkademyDb = actorRef.underlyingActor
        println(akkademyDb.map.get("Name"))
        akkademyDb.map.get("Name").toString should equal("K. Kishore")


      }
    }
  }

}
