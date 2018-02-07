package com.yih.paxos

class SnowFlake(dataCenterId:Long, machineId : Long) {
  private val START_STMP = 1480166465631L

  /**
    * 每一部分占用的位数
    */
  private val SEQUENCE_BIT = 12 //序列号占用的位数
  private val MACHINE_BIT = 5 //机器标识占用的位数
  private val DATACENTER_BIT = 5 //数据中心占用的位数

  /**
    * 每一部分的最大值
    */
  private val MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT)
  private val MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT)
  private val MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT)

  /**
    * 每一部分向左的位移
    */
  private val MACHINE_LEFT = SEQUENCE_BIT
  private val DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT
  private val TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT

  private var sequence = 0L //序列号
  private var lastStmp = -1L //上一次时间戳

  /**
    * 产生下一个ID
    *
    * @return
    */
  def nextId: Long = {
    var currStmp = getNewstmp
    if (currStmp < lastStmp) throw new RuntimeException("Clock moved backwards.  Refusing to generate id")
    if (currStmp == lastStmp) { //相同毫秒内，序列号自增
      sequence = (sequence + 1) & MAX_SEQUENCE
      //同一毫秒的序列数已经达到最大
      if (sequence == 0L) currStmp = getNextMill
    }
    else { //不同毫秒内，序列号置为0
      sequence = 0L
    }
    lastStmp = currStmp
    (currStmp - START_STMP) << TIMESTMP_LEFT | dataCenterId << DATACENTER_LEFT | machineId << MACHINE_LEFT | sequence
  }

  private def getNextMill = {
    var mill = getNewstmp
    while ( {
      mill <= lastStmp
    }) mill = getNewstmp
    mill
  }

  private def getNewstmp = System.currentTimeMillis
}