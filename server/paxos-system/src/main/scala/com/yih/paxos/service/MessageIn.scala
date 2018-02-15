package com.yih.paxos.service

import java.net.InetSocketAddress

class MessageIn[T](val uuid: Long, val remote: InetSocketAddress, val body: T) {

}
