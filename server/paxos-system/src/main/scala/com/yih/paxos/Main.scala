package com.yih.paxos

import com.yih.paxos.config.PaxosContextFactory
import com.yih.paxos.service.MessageService
import com.yih.paxos.service.paxos.Prepare
import com.yih.paxos.transition._

import scala.concurrent.Future
import scala.language.{higherKinds, implicitConversions}


// the following is equivalent to `implicit val ec = ExecutionContext.global`
import scala.concurrent.ExecutionContext.Implicits.global


object Main extends App {


    val c1 = PaxosContextFactory.loadConfig("application_0.conf")
    val c2 = PaxosContextFactory.loadConfig("application_1.conf")
    val c3 = PaxosContextFactory.loadConfig("application_2.conf")

    val port = 12346
    val server = new Server(List(), port)
    Future {
        server.run()
    }
    Thread.sleep(1000)
    val client = new Client("localhost", port)
    client.run()
    Thread.sleep(1000)

    //    val frame = new Frame(new Head(1), new Block(12345, 222), new Message(Verb.Echo, new Echo(123321, 1)))
    //    client.f.channel().writeAndFlush(frame)

    val snowFlake = new SnowFlake(100, 100)

    val iid = snowFlake.nextId
    MessageService.sendPrepare(new Prepare(iid, 100), client.f.channel())

    while (true) {

    }
}

