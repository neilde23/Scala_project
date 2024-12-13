import scala.util.Try

case class Point(x: Double, y: Double)

object Point {
    def lineToPoint(line: List[String]): Option[Point] = {
        line match {
            case x :: y :: Nil if Try(x.toDouble).isSuccess && Try(y.toDouble).isSuccess =>
                Some(Point(x.toDouble, y.toDouble))
            case _ => None
        }
    }
}