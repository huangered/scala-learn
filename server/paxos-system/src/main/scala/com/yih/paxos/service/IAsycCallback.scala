package com.yih.paxos.service

trait IAsycCallback {
    def doCallback(messageIn: AnyRef)
}
