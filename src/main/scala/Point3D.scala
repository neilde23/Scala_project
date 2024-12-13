import scala.util.Try

case class Point3D(x: Double, y: Double, z: Double)

object Point3D {
    def lineToPoint3D(line: List[String]): Option[Point3D] = {
        line match {
            case x :: y :: z :: Nil if Try(x.toDouble).isSuccess && Try(y.toDouble).isSuccess && Try(z.toDouble).isSuccess =>
                Some(Point3D(x.toDouble, y.toDouble, z.toDouble))
            case _ => None
        }
    }
}
