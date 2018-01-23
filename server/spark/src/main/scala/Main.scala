import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object Main extends App {
  var sc = new SparkContext(new SparkConf().setAppName("Spark count"))

  val threshold = args(1).toInt
  val tokenized = sc.textFile(args(0)).flatMap(_.split(" "))
  val wordCounts = tokenized.map((_,1)).reduceByKey(_ + _)
}