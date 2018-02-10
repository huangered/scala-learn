package com.yih.paxos.tool

import com.yih.paxos._
import com.yih.paxos.model._

object Convertor {
  def unpack(data: String): Packet = {

    //      System.out.println(data)
    //      return Echo(1)

    val splitData = data split ","
    val head = splitData(0)

    lazy val iid = splitData(1).toInt
    head match {
      case "register1" => Register1(iid, splitData(2))
      case "accept" => Accept(iid, splitData(2).toInt, splitData(3))
      case "prepare" => Prepare(iid, splitData(2).toInt)
      case "aok" => Aok(iid)
      case "pok" => Pok(iid)
      case "pok2" => Pok2(iid, splitData(2).toInt, splitData(3))
      case "error1" => Error1(iid)
      case "error2" => Error2(iid)
      case "store" => Store(iid, splitData(2))
      case "echo" => Echo()
      case _ => Unknown(data)

    }
  }

  def pack(packet: Packet): String = {
    packet match {
      case Register1(iid, value) => s"register1,$iid,$value"
      case Accept(iid, pid, value) => s"accept,$iid,$pid,$value"
      case Prepare(iid, pid) => s"prepare,$iid,$pid"
      case Aok(iid) => s"aok,$iid"
      case Pok(iid) => s"pok,$iid"
      case Pok2(iid, a, v) => s"pok2,$iid,$a,$v"
      case Error1(iid) => s"error1,$iid"
      case Error2(iid) => s"error2,$iid"
      case Store(iid, value) => s"store,$iid,$value"
      case Echo() => s"echo"
      case _ => "unknown"
    }
  }
}