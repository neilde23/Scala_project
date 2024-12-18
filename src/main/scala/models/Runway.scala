package models

case class Runway(
                   id: String,
                   airportRef: String,
                   airportIdent: String,
                   lengthFt: String,
                   widthFt: String,
                   surface: String,
                   lighted: String,
                   closed: String,
                   leIdent: String,
                   heIdent: String
                 )

object Runway {
  /**
   * Convertit une ligne CSV en objet Runway.
   * @param cols Liste des colonnes filtrées.
   * @return Option[Runway] si les données sont valides.
   */
  def fromCsv(cols: List[String]): Option[Runway] = {
    if (cols.length >= 10) { // Vérifie qu'il y a au moins 10 colonnes valides
      Some(Runway(
        id = cols(0),
        airportRef = cols(1),
        airportIdent = cols(2),
        lengthFt = cols(3),
        widthFt = cols(4),
        surface = cols(5),
        lighted = cols(6),
        closed = cols(7),
        leIdent = cols(8),
        heIdent = cols(9)
      ))
    } else {
      None
    }
  }
}
