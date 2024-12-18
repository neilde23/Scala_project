package models

case class Country(
                    id: String,
                    code: String,
                    name: String,
                    continent: String,
                    wikipediaLink: String
                  )

object Country {
  /**
   * Convertit une ligne CSV en objet Country.
   * @param cols Liste des colonnes filtrées.
   * @return Option[Country] si les données sont valides.
   */
  def fromCsv(cols: List[String]): Option[Country] = {
    if (cols.length >= 5) { // Vérifie qu'il y a au moins 5 colonnes valides
      Some(Country(
        id = cols(0),            // Correspond à "id"
        code = cols(1),          // Correspond à "code"
        name = cols(2),          // Correspond à "name"
        continent = cols(3),     // Correspond à "continent"
        wikipediaLink = cols(4)  // Correspond à "wikipedia_link"
      ))
    } else {
      None // Retourne None si la ligne est invalide
    }
  }
}
