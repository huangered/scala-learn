package com.yih.paxos.service

import com.yih.paxos.net.EchoVerbHandler

import scala.collection.mutable

object MessageService {
    val verbs = Map(
        Verb.Echo -> new EchoVerbHandler,
        Verb.Commit -> new CommitVerbHandler,
        Verb.Prepare -> new PrepareVerbHandler,
        Verb.Propose -> new ProposeVerbHandler
    )

    val callbacks: mutable.Map[Long, IAsycCallback] = mutable.Map()

    def send[T](messageOut: MessageOut[T]): Unit = {

    }
}