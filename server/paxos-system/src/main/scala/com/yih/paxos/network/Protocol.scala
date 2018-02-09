package com.yih.paxos.network

import com.yih.paxos.model.Packet

case class Send(packet: Packet)

case class Receive(packet: Packet)