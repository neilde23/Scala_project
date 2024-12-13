import scala.io.Source
import scala.io.BufferedSource
import scala.util.Try

class csvToPoint {
    def fileToPoint[T](path: String, f: List[String] => Option[T]): List[T] = {
        val file: BufferedSource = Source.fromFile(path)
        try {
            file
                .getLines()
                .drop(1)
                .map(_.split(",").toList)
                .flatMap(f)
                .toList
//        } catch {
//            // error message is printed to the console
//            case e: Exception => 
        } finally {
            file.close()
        }
    }
}
