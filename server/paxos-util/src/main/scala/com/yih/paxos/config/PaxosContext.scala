package com.yih.paxos.config

class PaxosContext(val ip:String, val port:Int, role:String) {
  override def toString: String = s"address {$ip:$port}, role {$role}"
}
