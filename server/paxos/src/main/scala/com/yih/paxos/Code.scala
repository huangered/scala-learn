package com.yih.paxos

object Code extends Enumeration {
  type Code = Value
  val Accept, Prepare, Aok, Pok, Error1, Error2, Store = Value
}
