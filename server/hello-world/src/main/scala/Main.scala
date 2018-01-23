import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {


  val lastInteger = new AtomicInteger
  def futureInt() = future {
    Thread sleep 2000
    lastInteger incrementAndGet
  }

  // use callbacks for completion of futures
  val a1 = futureInt
  val a2 = futureInt
  a1.onSuccess {
    case i1 => {
      a2.onSuccess {
        case i2 => println("Sum of values is " + (i1 + i2))
      }
    }
  }
  a1 onComplete( {
    case i1=> println("a1 complete")
  })
  Thread sleep 3000

  // use for construct to extract values when futures complete
  val b1 = futureInt
  val b2 = futureInt
  for (i1 <- b1; i2 <- b2) yield println("Sum of values is " + (i1 + i2))
  Thread sleep 3000

  // wait directly for completion of futures
  val c1 = futureInt
  val c2 = futureInt
  println("Sum of values is " + (Await.result(c1, Duration.Inf) + Await.result(c2, Duration.Inf)))
}