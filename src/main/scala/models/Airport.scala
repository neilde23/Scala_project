package models

case class Airport(
                    id: String,
                    ident: String,
                    airportType: String,
                    name: String,
                    latitudeDeg: String,
                    longitudeDeg: String,
                    elevationFt: String,
                    continent: String,
                    isoCountry: String,
                    isoRegion: String,
                    municipality: String,
                    scheduledService: String,
                    gpsCode: String
                  )

object Airport {
  /**
   * Convertit une ligne CSV en objet Airport.
   * @param cols Liste des colonnes filtrées.
   * @return Option[Airport] si les données sont valides.
   */
  def fromCsv(cols: List[String]): Option[Airport] = {
    if (cols.length >= 13) { // Vérifie qu'il y a au moins 13 colonnes valides
      Some(Airport(
        id = cols(0),                // Correspond à "id"
        ident = cols(1),             // Correspond à "ident"
        airportType = cols(2),       // Correspond à "type"
        name = cols(3),              // Correspond à "name"
        latitudeDeg = cols(4),       // Correspond à "latitude_deg"
        longitudeDeg = cols(5),      // Correspond à "longitude_deg"
        elevationFt = cols(6),       // Correspond à "elevation_ft"
        continent = cols(7),         // Correspond à "continent"
        isoCountry = cols(8),        // Correspond à "iso_country"
        isoRegion = cols(9),         // Correspond à "iso_region"
        municipality = cols(10),     // Correspond à "municipality"
        scheduledService = cols(11), // Correspond à "scheduled_service"
        gpsCode = cols(12)           // Correspond à "gps_code"
      ))
    } else {
      None // Retourne None si la ligne est invalide
    }
  }
}
