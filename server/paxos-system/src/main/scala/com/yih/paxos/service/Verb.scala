package com.yih.paxos.service


object Verb extends Enumeration {
    type Verb = Value
    val Echo = Value(0, "echo")
    val Prepare = Value(1, "prepare")
    val Propose = Value(2, "propose")
    val Commit = Value(3, "commit")
    val PrepareResponse = Value(4, "prepareresponse")
    val ProposeResponse = Value(5, "proposeresponse")
    val Unknown = Value(100, "unknown")
}