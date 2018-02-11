package com.yih.paxos.config

class PaxosContext(ip:String, port:Int, role:String) {
  override def toString: String = s"address {$ip:$port}, role {$role}"
}
