package com.yih.paxos.service

import com.yih.paxos.serializer.EchoSerializer

import scala.collection.mutable

object MessageService {
  val verbs = Map(
    Verb.Echo -> new EchoVerbHandler,
    Verb.Commit -> new CommitVerbHandler,
    Verb.Prepare -> new PrepareVerbHandler,
    Verb.Propose -> new ProposeVerbHandler
  )

  val serializers = Map(Verb.Echo -> new EchoSerializer)

  val callbacks:mutable.Map[Long, IAsycCallback[_]] = mutable.Map()

  def send[T](messageOut: MessageOut[T]): Unit = {

  }
}
