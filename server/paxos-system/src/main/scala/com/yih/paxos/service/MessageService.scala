package com.yih.paxos.service

import com.typesafe.scalalogging.Logger
import com.yih.paxos.SnowFlake
import com.yih.paxos.codec.Icodec
import com.yih.paxos.net.{Echo, EchoVerbHandler}
import com.yih.paxos.service.paxos._
import com.yih.paxos.transition._
import io.netty.channel.Channel

import scala.collection.mutable

object MessageService {
    val logger = Logger("MessageService")

    val snowFlake = new SnowFlake(1, 1)

    val verbs = Map(
        Verb.Echo -> new EchoVerbHandler,
        Verb.Commit -> new CommitVerbHandler,
        Verb.Prepare -> new PrepareVerbHandler,
        Verb.Propose -> new ProposeVerbHandler
    )

    val codecs: Map[Verb.Value, Icodec[_ <: Commit]] = Map(
        //        Verb.Echo -> Echo.codec,
        Verb.Prepare -> Prepare.codec,
        Verb.PrepareResponse -> PrepareResponse.codec,
        Verb.Propose -> Propose.codec,
        Verb.ProposeResponse -> ProposeResponse.codec
    )

    val callbacks: mutable.Map[Long, IAsycCallback] = mutable.Map()

    def sendPrepare(prepare: Prepare, channel: Channel): Unit = {
        logger.info("Commit iid {}", prepare.iid)
        val traceId = snowFlake.nextId
        val frame = new Frame(new Head(Version.v1), new Block(traceId, 256), new Message(Verb.Prepare, prepare))
        val callback = new PrepareCallback(1)
        MessageService.callbacks.put(prepare.iid, callback)
        logger.info("{}", MessageService.callbacks)
        channel.writeAndFlush(frame)
        callback.await()
        logger.info("Receive prepare response")

        val frame2 = new Frame(new Head(Version.v1), new Block(traceId, 256), new Message(Verb.Propose, new Propose(prepare.iid, prepare.proposeId, 123)))
        val callback2 = new ProposeCallback(1)
        MessageService.callbacks.put(prepare.iid, callback2)
        logger.info("{}", MessageService.callbacks)

        channel.writeAndFlush(frame2)
        callback2.await()
        logger.info("Receive propose response")

    }

    def sendOne[T](messageOut: MessageOut[T], to: (String, Int)): Unit = {
        send[T](messageOut, to)
    }

    def send[T](messageOut: MessageOut[T], to: (String, Int)): Unit = {
        val body = messageOut.body.asInstanceOf[AnyRef]

        val msg = messageOut.body match {
            case echo: Echo => new Message(Verb.Echo, body)
            case prepare: Prepare => new Message(Verb.Prepare, body)
            case pp: PrepareResponse => new Message(Verb.PrepareResponse, body)
            case propose: Propose => new Message(Verb.Propose, body)
            case pr: ProposeResponse => new Message(Verb.ProposeResponse, body)
        }

        val frame = new Frame(new Head(1), new Block(12345, 222), msg)

        val ip = to._1
        val port = to._2
        val key = s"$ip:$port"
        // register callback
        ConnectionService.connections(key).writeAndFlush(frame)
    }
}