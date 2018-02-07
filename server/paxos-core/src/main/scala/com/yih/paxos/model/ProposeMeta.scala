package com.yih.paxos.model

class ProposeMeta(var proposeId: Int,
                  var prepareNum: Int, var notPrepareNum: Int,
                  var acceptNum: Int, var notAcceptNum: Int,
                  var maxAcceptId: Int,
                  var acceptValue: AnyRef) {
}