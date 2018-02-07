package com.yih.paxos

class Propose(var proposeId: Int, var prepareNum: Int,
              var acceptNum: Int, var maxAcceptId: Int,
              var acceptValue: AnyRef) {

}
