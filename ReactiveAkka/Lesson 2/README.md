# Reactive Akka Lesson 1
In this chapter, we showed you how actors and threads are related and how the dispatcher plays a key role in the ActorSystem. We gave a broad high level introduction to what Akka is capable of doing and the components such as Mailbox queues and dequeues messages.

When an actor dies, all the messages go to the deadletters. We saw how one can subscribe to deadletters via EventStream and get the messages. Actor DeathWatch is another feature where one actor can find out the status of any other actor. Actor life cycle is another important concept to handle the internal state of the actor itself, and comes in handy when dealing with actor restarts and initializations. We also saw how the actors themselves can be supervised using ActorSupervision and its strategies.
