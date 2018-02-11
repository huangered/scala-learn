package com.yih.paxos.config

import com.typesafe.config.ConfigFactory

object PaxosContextFactory {
  def loadConfig(path: String = "application.conf"): PaxosContext = {
    val conf = ConfigFactory.load(path)
    val ip = conf.getString("server.ip")
    val port = conf.getInt("server.port")
    val role = conf.getString("server.role")

    new PaxosContext(ip, port, role)
  }
}