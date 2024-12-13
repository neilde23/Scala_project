import scala.util.Try

object Main extends App {
    val csvToPoint = new csvToPoint
    val points = csvToPoint.fileToPoint("src/main/resources/point2D.csv", Point.lineToPoint)
    val points3D = csvToPoint.fileToPoint("src/main/resources/point3D.csv", Point3D.lineToPoint3D)
    println(points)
    println(points3D)
}