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

  var root = system.actorOf(Props(classOf[RootActor], List(("localhost", port_0), ("localhost", port_1), ("localhost", port_2))), "root")
  Thread.sleep(5 * 1000)
  for (i <- 1 to 10) {
    root ! "echo"
  }
  root ! "test"
}