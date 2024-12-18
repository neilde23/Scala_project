import parser.CsvParser
import models.{Runway, Airport, Country}

object Main {
    def main(args: Array[String]): Unit = {
        val fileNameRunways = "/runways.csv"
        val fileNameAirports = "/airports.csv"
        val fileNameCountries = "/countries.csv"

        // === Parsing des Runways ===
        println("\n=== Parsing Runways ===")
        val runways = CsvParser.parseCsv(fileNameRunways, Runway.fromCsv, 0.7)
        runways match {
            case Right(runwaysList) if runwaysList.nonEmpty =>
                println(s"\nNombre total de pistes valides : ${runwaysList.size}")
                println("\nExemple des 5 premières pistes :")
                runwaysList.take(5).foreach(println)

            case Right(_) =>
                println("Aucune piste valide trouvée.")

            case Left(errorMessage) =>
                println(s"Erreur lors du parsing des pistes : $errorMessage")
        }

        // === Parsing des Airports ===
        println("\n=== Parsing Airports ===")
        val airports = CsvParser.parseCsv(fileNameAirports, Airport.fromCsv, 0.7)
        airports match {
            case Right(airportsList) if airportsList.nonEmpty =>
                println(s"\nNombre total d'aéroports valides : ${airportsList.size}")
                println("\nExemple des 5 premiers aéroports :")
                airportsList.take(5).foreach(println)

            case Right(_) =>
                println("Aucun aéroport valide trouvé.")

            case Left(errorMessage) =>
                println(s"Erreur lors du parsing des aéroports : $errorMessage")
        }

        // === Parsing des Countries ===
        println("\n=== Parsing Countries ===")
        val countries = CsvParser.parseCsv(fileNameCountries, Country.fromCsv, 0.7)
        countries match {
            case Right(countriesList) if countriesList.nonEmpty =>
                println(s"\nNombre total de pays valides : ${countriesList.size}")
                println("\nExemple des 5 premiers pays :")
                countriesList.take(5).foreach(println)

            case Right(_) =>
                println("Aucun pays valide trouvé.")

            case Left(errorMessage) =>
                println(s"Erreur lors du parsing des pays : $errorMessage")
        }
    }
}
