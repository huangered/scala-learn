package com.yih.paxos


import scala.collection.mutable.Map

object Helper {
  var i: Int = 0

  def addKey(map: Map[Int, Propose], key: Int): Unit = {
    if (!map.contains(key)) {
      val p = new Propose(0, 0, 0, 0, null)
      map += (key -> p)
    }
  }

  def timestamp(): Int = {
    i += 1
    return i
  }
}