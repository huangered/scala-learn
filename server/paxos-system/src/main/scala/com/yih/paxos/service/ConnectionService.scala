package com.yih.paxos.service

import io.netty.channel.Channel

import scala.collection.mutable

object ConnectionService {
    val connections: mutable.Map[String, Channel] = mutable.Map()
}
