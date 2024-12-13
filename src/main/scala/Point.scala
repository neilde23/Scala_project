import scala.util.Try

case class Point(x: Double, y: Double)

object Point {
    def csvToPoint(line: List[String]): Option[Point] = {
        if (line.length == 2) {
            Try {
                val x = line.head.toDouble
                val y = line(1).toDouble
                Point(x, y)
            }.toOption
        } else {
            None
        }
    }
}

object Main extends App {
    println(Point.csvToPoint("42" :: "21" :: Nil))
    println(Point.csvToPoint("42" :: "invalid" :: Nil))
    println(Point.csvToPoint("42" :: Nil))
}