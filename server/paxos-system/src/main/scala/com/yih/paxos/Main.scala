package com.yih.paxos

import akka.actor.{ActorSystem, Props}
import com.yih.paxos.network.server.{ServerHandler, SimpleServer}

object Main extends App {
  var port_0 = 40200
  var port_1 = 40101
  var port_2 = 40102
  val system = ActorSystem("test")
  val handler_0 = system.actorOf(Props(classOf[ServerHandler]), "server_handler_0")
  val handler_1 = system.actorOf(Props(classOf[ServerHandler]), "server_handler_1")
  val handler_2 = system.actorOf(Props(classOf[ServerHandler]), "server_handler_2")
  //
  val server_0 = system.actorOf(Props(classOf[SimpleServer], "localhost", port_0, handler_0), "server0")
  val server_1 = system.actorOf(Props(classOf[SimpleServer], "localhost", port_1, handler_1), "server1")
  val server_2 = system.actorOf(Props(classOf[SimpleServer], "localhost", port_2, handler_2), "server2")


  Thread.sleep(5 * 1000)

  //var handler2 = system.actorOf(Props[ListenerActor2], "ddd")
  //var client = system.actorOf(Props(classOf[RootActor], new InetSocketAddress("localhost", 30000), handler2))
  var root = system.actorOf(Props(classOf[RootActor], List(("localhost", port_0),("localhost", port_1),("localhost", port_2))), "root")
  Thread.sleep(3 * 1000)
  root ! "test"
  //  val a0 = system.actorOf(Props(classOf[AcceptorActor]), "acceptor-0");
  //  val a1 = system.actorOf(Props(classOf[AcceptorActor]), "acceptor-1");
  //  val a2 = system.actorOf(Props(classOf[AcceptorActor]), "acceptor-2");
  //
  //  val l = List(a0, a1, a2)
  //
  //  val le0 = system.actorOf(Props(classOf[LearnerActor]), "learner-0")
  //  val le1 = system.actorOf(Props(classOf[LearnerActor]), "learner-1")
  //  val le2 = system.actorOf(Props(classOf[LearnerActor]), "learner-2")
  //
  //  val le = List(le0, le1, le2)
  //
  // var p0 = system.actorOf(Props(classOf[ProposerActor], l, le), "proposer-0")
  //
  //  p0 ! Register1(3, "ddddd")
  //

  //  Thread.sleep(1* 1000)
  //  val addr = new InetSocketAddress("localhost", 3000)
  //  val listener = system.actorOf(Props[ListenerActor], "listener")
  //  val client = system.actorOf(Props(classOf[Client], addr, listener), "client")
  //  Thread.sleep(5 * 1000)
  //  client ! Convertor.apply2(Prepare(100, 200))
}