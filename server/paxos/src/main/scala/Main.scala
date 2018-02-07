import akka.actor.{ActorSystem, Props}
import com.yih.paxos.Code._
import com.yih.paxos._
import com.yih.paxos.actor.{AcceptorActor, LearnerActor, ProposerActor}
import com.yih.paxos.network.SimpleServer

object Main extends App {
  val system = ActorSystem("test")
  val server = system.actorOf(Props[SimpleServer], "server")

  val a0 = system.actorOf(Props(classOf[AcceptorActor]), "acceptor-0");
  val a1 = system.actorOf(Props(classOf[AcceptorActor]), "acceptor-1");
  val a2 = system.actorOf(Props(classOf[AcceptorActor]), "acceptor-2");

  val l = List(a0, a1, a2)

  val le0 = system.actorOf(Props(classOf[LearnerActor]), "learner-0")
  val le1 = system.actorOf(Props(classOf[LearnerActor]), "learner-1")
  val le2 = system.actorOf(Props(classOf[LearnerActor]), "learner-2")

  val le = List(le0, le1, le2)

  var p0 = system.actorOf(Props(classOf[ProposerActor], l, le), "proposer-0")

  p0 ! (Prepare, 3, "ddddd")
}