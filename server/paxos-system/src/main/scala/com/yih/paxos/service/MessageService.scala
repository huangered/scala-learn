package com.yih.paxos.service

import com.yih.paxos.net.{Echo, EchoVerbHandler}
import com.yih.paxos.serializer.EchoSerializer
import com.yih.paxos.service.paxos.Commit

import scala.collection.mutable

object MessageService {
  val verbs = Map(
    Verb.Echo -> new EchoVerbHandler,
    Verb.Commit -> new CommitVerbHandler,
    Verb.Prepare -> new PrepareVerbHandler,
    Verb.Propose -> new ProposeVerbHandler
  )

  val serializers = Map(Verb.Echo -> new EchoSerializer)

  val callbacks: mutable.Map[Long, IAsycCallback[_ >: Echo with Commit <: AnyRef]] = mutable.Map()

  def send[T](messageOut: MessageOut[T]): Unit = {

  }
}