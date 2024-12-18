package parser

import scala.io.Source
import scala.util.Try

object CsvParser {

  /**
   * Parse un fichier CSV en filtrant les colonnes et lignes valides.
   * @param fileName Chemin du fichier CSV.
   * @param lineParser Fonction pour convertir une ligne filtrée en objet Scala.
   * @param threshold Pourcentage minimal de remplissage pour valider colonnes/lignes (ex: 0.7 = 70 %).
   * @tparam A Type de l'objet à retourner.
   * @return Soit une liste d'objets parsés, soit un message d'erreur.
   */
  def parseCsv[A](fileName: String, lineParser: List[String] => Option[A], threshold: Double = 0.7): Either[String, List[A]] = {
    val resource = Option(getClass.getResource(fileName))
    if (resource.isEmpty) {
      return Left(s"Erreur : Fichier $fileName introuvable.")
    }

    val lines = Try(Source.fromFile(resource.get.getPath).getLines().toList).toOption.getOrElse {
      return Left("Erreur : Impossible de lire le fichier.")
    }

    if (lines.isEmpty) {
      return Left("Erreur : Le fichier est vide.")
    }

    val header = lines.head.split(",").map(_.trim).toList
    val dataLines = lines.tail.map(_.split(",").map(_.trim).toList)

    // Étape 1 : Calculer le taux de remplissage pour chaque colonne
    val columnNonEmptyCounts = header.indices.map { colIndex =>
      dataLines.count(line => line.isDefinedAt(colIndex) && line(colIndex).nonEmpty)
    }

    // Afficher les taux de remplissage par colonne
    println("\nTaux de remplissage par colonne :")
    header.indices.foreach { colIndex =>
      val nonEmptyCount = columnNonEmptyCounts(colIndex)
      val fillRate = (nonEmptyCount.toDouble / dataLines.size) * 100
      println(f"Colonne '${header(colIndex)}' : $nonEmptyCount / ${dataLines.size} (${fillRate}%.2f%%)")
    }

    // Étape 2 : Sélectionner les colonnes valides
    val validColumns = columnNonEmptyCounts.zipWithIndex.filter {
      case (count, _) => count.toDouble / dataLines.size >= threshold
    }.map(_._2)

    if (validColumns.isEmpty) {
      return Left("\nErreur : Aucune colonne n'atteint le seuil minimal de remplissage. Parsing abandonné.")
    }

    // Afficher les colonnes retenues
    println(s"\nColonnes retenues : ${validColumns.map(header).mkString(", ")}")

    // Étape 3 : Filtrer et parser les lignes
    val parsedLines = dataLines.flatMap { line =>
      val filteredLine = validColumns.map(index => if (line.isDefinedAt(index)) line(index) else "").toList
      lineParser(filteredLine)
    }

    // Vérifier le taux de lignes valides
    val validRate = parsedLines.size.toDouble / dataLines.size
    if (validRate < threshold) {
      Left(f"\nErreur : Trop de lignes sont incomplètes (${validRate * 100}%.2f%% valides). Parsing abandonné.")
    } else {
      Right(parsedLines)
    }
  }
}
