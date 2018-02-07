package com.yih.paxos.network.server

import java.nio.charset.Charset

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import akka.util.ByteString
import com.yih.paxos.actor.{AcceptorActor, LearnerActor}
import com.yih.paxos.{Convertor, Packet, Prepare, Accept, Store}


class ServerHandler extends Actor {

  import akka.io.Tcp._

  val log = Logging(context.system, this)

  val acceptor: ActorRef = context.actorOf(Props(classOf[AcceptorActor]), "acceptor-0")

  val learner: ActorRef = context.actorOf(Props(classOf[LearnerActor]), "learner-0")

  def receive = {
    case Received(data) => {
      val connection = sender()
      val str = data.decodeString(Charset.defaultCharset())
      log.info(s"server receive $str from $connection")
      val packet = Convertor.unpack(str)
      acceptor ! packet
      context become {
        case Received(data) => {
          val str = data.decodeString(Charset.defaultCharset())
          log.info(s"server receive $str from $connection")
          val packet = Convertor.unpack(str)
          packet match {
            case Accept(_, _, _) | Prepare(_, _) => acceptor ! packet
            case Store(_, _) => learner ! packet
          }
        }
        case packet: Packet => {
          val data: String = Convertor.pack(packet)
          log.info(s"send $data to client by $connection")
          connection ! Write(ByteString(data))
        }
        case any => {
          log.error(s"unknown error in server handler $any")
        }
      }
    }
    case PeerClosed => context stop self
    case other => log.info(s"other $other")
  }
}