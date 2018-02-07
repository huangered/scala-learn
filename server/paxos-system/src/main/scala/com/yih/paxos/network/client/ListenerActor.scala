package com.yih.paxos.network.client

import java.nio.charset.Charset

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import akka.io.Tcp.{Connected, Write}
import akka.util.ByteString
import com.yih.paxos.network.{Receive, Send}
import com.yih.paxos.{Convertor, Packet}

class ListenerActor(proposer1: ActorRef) extends Actor {
  val log = Logging(context.system, this)

  val proposer = proposer1

  def receive = {
    case Connected(remote, local) => {
      log.info("Listener receive connected ")
      val connection = sender()
      context become {
        case Send(packet: Packet) => {
          val data: String = Convertor.pack(packet)
          connection ! ByteString(data)
        }
        case Receive(packet: Packet) => {
          proposer ! packet
        }
        case any => {
          log.error(s"unknown message $any")
        }
      }
    }
    case data: ByteString => {
      val connection = sender()
      log.info(s"call back $data")
      // sender() ! data
      var ss = data.decodeString(Charset.defaultCharset())
      log.info(s"data $ss")
      var packet = Convertor.unpack(ss)
      proposer ! packet
      context become {
        case any: Packet => {
          val data: String = Convertor.pack(any)
          connection ! Write(ByteString(data))
        }
        case _ => {
          log.error("unknown")
        }
      }
    }
    case any =>
      log.info(s"received $any callback")
  }
}