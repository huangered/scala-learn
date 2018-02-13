package com.yih.paxos.service

import com.yih.paxos.service.paxos.Commit

trait IVerbHandler[T] {
  def doVerb(elem: MessageIn[T]): Unit
}

class CommitVerbHandler extends IVerbHandler[Commit] {
  override def doVerb(elem: MessageIn[Commit]): Unit = {}
}

class PrepareVerbHandler extends IVerbHandler[Commit] {
  override def doVerb(elem:  MessageIn[Commit]): Unit = {}
}

class ProposeVerbHandler extends IVerbHandler[Commit] {
  override def doVerb(elem:  MessageIn[Commit]): Unit = {}
}