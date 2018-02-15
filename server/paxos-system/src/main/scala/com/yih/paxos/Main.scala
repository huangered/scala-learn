package com.yih.paxos

import com.yih.paxos.config.PaxosContextFactory
import com.yih.paxos.transition._
import io.netty.buffer.Unpooled

import scala.concurrent.Future
import scala.language.{higherKinds, implicitConversions}


// the following is equivalent to `implicit val ec = ExecutionContext.global`
import scala.concurrent.ExecutionContext.Implicits.global


object Main extends App {

    val c1 = PaxosContextFactory.loadConfig("application_0.conf")
    val c2 = PaxosContextFactory.loadConfig("application_1.conf")
    val c3 = PaxosContextFactory.loadConfig("application_2.conf")

    val port = 12345
    val server = new Server(List(), port)
    Future {
        server.run()
    }
    Thread.sleep(1000)
    val client = new Client("localhost", port)
    client.run()
    Thread.sleep(1000)

    val frame = new Frame(new Head(1), new Block(12345, 222), null)
    client.f.channel().writeAndFlush(frame)

    while (true) {

    }
}