package com.yih.paxos.model

import com.yih.paxos.model.Role.Role

object Role extends Enumeration {
  type Role = Value
  val Acceptor, Proposer, Learner = Value
}

trait Packet {}

case class Register1(iid: Int, value: AnyRef) extends Packet

case class Accept(iid: Int, proposeId: Int, acceptValue: AnyRef) extends Packet

case class Prepare(iid: Int, proposeId: Int) extends Packet

case class Aok(iid: Int) extends Packet

case class Pok(iid: Int) extends Packet

case class Pok2(iid: Int, a: Int, v: AnyRef) extends Packet

case class Atimeout(iid: Int) extends Packet

case class Ptimeout(iid: Int) extends Packet

case class Error1(iid: Int) extends Packet

case class Error2(iid: Int) extends Packet

case class Store(iid: Int, acceptValue: AnyRef) extends Packet

case class Unknown(data: String) extends Packet

case class Msg(role: Role, data: String) extends Packet

case class Echo(iid: Int) extends Packet