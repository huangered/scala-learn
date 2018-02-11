package com.yih.paxos

import akka.actor.{ActorSystem, Props}
import com.yih.paxos.config.PaxosContextFactory
import com.yih.paxos.network.server.{ServerHandler, SimpleServer}

object Main extends App {

  val c1 = PaxosContextFactory.loadConfig("application_0.conf")
  val c2 = PaxosContextFactory.loadConfig("application_1.conf")
  val c3 = PaxosContextFactory.loadConfig("application_2.conf")

  val system = ActorSystem("test")
  val handler_0 = system.actorOf(Props(classOf[ServerHandler]), "server_handler_0")
  val handler_1 = system.actorOf(Props(classOf[ServerHandler]), "server_handler_1")
  val handler_2 = system.actorOf(Props(classOf[ServerHandler]), "server_handler_2")
  //
  val server_0 = system.actorOf(Props(classOf[SimpleServer], c1, handler_0), "server0")
  val server_1 = system.actorOf(Props(classOf[SimpleServer], c2, handler_1), "server1")
  val server_2 = system.actorOf(Props(classOf[SimpleServer], c3, handler_2), "server2")


  Thread.sleep(5 * 1000)

  var root = system.actorOf(Props(classOf[RootActor], List(c1, c2, c3)), "root")
  Thread.sleep(5 * 1000)
  for (i <- 1 to 10) {
    root ! "echo"
  }
  root ! "test"
}