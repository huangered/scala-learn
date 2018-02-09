package com.yih.paxos.tool

import com.yih.paxos.model.{AcceptMeta, ProposeMeta}

import scala.collection.mutable.Map

object Helper {
  var i: Int = 0

  def addKey(map: Map[Int, ProposeMeta], key: Int): Unit = {
    if (!map.contains(key)) {
      val p = new ProposeMeta(0, 0, 0, 0, 0, 0, null)
      map += (key -> p)
    }
  }

  def addKey2(map: Map[Int, AcceptMeta], key: Int): Unit = {
    if (!map.contains(key)) {
      val a = new AcceptMeta(0, 0, null)
      map += (key -> a)
    }
  }

  def timestamp(): Int = {
    i += 1
    i
  }
}