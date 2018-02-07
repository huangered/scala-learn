package com.yih.paxos.model

class AcceptMeta(var maxProposeId: Int,
                 var acceptProposeId: Int,
                 var acceptValue: AnyRef) {

}
