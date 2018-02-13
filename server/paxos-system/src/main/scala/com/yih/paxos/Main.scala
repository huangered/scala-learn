package com.yih.paxos

import akka.actor.{ActorSystem, Props}
import com.yih.paxos.config.PaxosContextFactory
import com.yih.paxos.network.server.{ServerHandler, SimpleServer}
import com.yih.paxos.service.{MessageService, Verb}

import scala.collection.JavaConverters._

object Main extends App {

  val c1 = PaxosContextFactory.loadConfig("application_0.conf")
  val c2 = PaxosContextFactory.loadConfig("application_1.conf")
  val c3 = PaxosContextFactory.loadConfig("application_2.conf")

}
