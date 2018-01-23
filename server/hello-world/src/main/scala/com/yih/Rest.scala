package com.yih

trait Base {
  override def toString: String = "Base"
}

class A extends Base {
  override def toString: String = "A ->" + super.toString;
}

trait B extends Base {
  override def toString: String = "B ->" + super.toString;
}

class Rest extends A with B {
  def test: Unit = {
    println("hello world")
  }

  def hello: String = {
    return "abc"
  }

  def cal(amt: Double): String = f"€$amt%.2f"

  def cal_1(amt: Double =10.0): String = f"€$amt%.2f"

  def cal_2[A](amt: A): Unit = {
    println(amt.getClass())
    println(amt)
  }
}

class Util {
  def test(data:Int):String ={
    data match {
      case 1 => "1"
      case 2=> "2"
      case _ => "3"
    }
  }
}

object Help {
  def ggg():Unit = {
    println("ggg")
  }
}