package UserInterface

object CLI {
  def main(args: Array[String]): Unit = {
    displayWelcomeMessage()
    var continue = true

    while (continue) {
      displayMainMenu()
      val choice = scala.io.StdIn.readLine().trim.toLowerCase

      choice match {
        case "1" => handleQuery()
        case "2" => handleReports()
        case "help" => displayHelp()
        case "q" =>
          println(Console.YELLOW + "Merci d'avoir utilisé le programme. Au revoir !" + Console.RESET)
          continue = false
        case _ =>
          println(Console.RED + "Option invalide. Veuillez entrer 1, 2, help ou q pour quitter." + Console.RESET)
      }
    }
  }
}

// Display the welcome message
def displayWelcomeMessage(): Unit = {
  println(Console.GREEN + "Bienvenue dans le programme de recherche de données aéroportuaires !" + Console.RESET)
}

// Display the main menu
def displayMainMenu(): Unit = {
  println(Console.BLUE + "Menu Principal :" + Console.RESET)
  println(Console.YELLOW + "1. Query - Rechercher des informations " + Console.RESET)
  println(Console.YELLOW + "2. Reports - Générer des rapports" + Console.RESET)
  println(Console.YELLOW + "help - Afficher l'aide" + Console.RESET)
  println(Console.YELLOW + "q. Quitter" + Console.RESET)
  print(Console.YELLOW + "Entrez votre choix : " + Console.RESET)
}

// Display the help message
def displayHelp(): Unit = {
  println(Console.BLUE + "Aide :" + Console.RESET)
  println(Console.YELLOW + "1. Query - Rechercher des informations : Permet de rechercher des informations sur les aéroports selon les noms ou codes des pays." + Console.RESET)
  println(Console.YELLOW + "2. Reports - Générer des rapports : Permet de générer des rapports sur les aéroports, les pistes et les pays." + Console.RESET)
  println(Console.YELLOW + "help - Afficher l'aide : Affiche ce message d'aide." + Console.RESET)
  println(Console.YELLOW + "q. Quitter : Quitte le programme." + Console.RESET)
}

// Handle the query option
def handleQuery(): Unit = {
  println("\n--- Rechercher les informations sur un pays ---")
  print(Console.YELLOW + "Entrez le nom ou le code du pays : " + Console.RESET)
  val input = scala.io.StdIn.readLine().trim

  if (input.isEmpty) {
    println(Console.RED + "Erreur : La saisie est vide. Veuillez entrer un nom ou un code valide." + Console.RESET)
  } else {
    Storage.queryCountry(input) match {
      case Some(results) if results.nonEmpty =>
        println(s"\nRésultats pour '$input' :")
        paginateResults(results)
      case Some(_) =>
        println(Console.RED + "Erreur : Aucune donnée trouvée pour ce pays." + Console.RESET)
      case None =>
        println(Console.RED + "Erreur : Le pays spécifié est introuvable dans la base de données." + Console.RESET)
    }
  }
}

// Handle the reports option
def handleReports(): Unit = {
  println("\n--- Générer des rapports ---")
  println(Console.YELLOW + "1. Top 10 pays avec le plus grand et le plus petit nombre d'aéroports" + Console.RESET)
  println(Console.YELLOW + "2. Types de pistes par pays" + Console.RESET)
  println(Console.YELLOW + "3. Top 10 des latitudes de pistes les plus courantes" + Console.RESET)
  print(Console.YELLOW + "Rentrez votre choix : " + Console.RESET)
  val choice = scala.io.StdIn.readLine().trim

  if (!choice.matches("[1-3]")) {
    println(Console.RED + "Erreur : Veuillez entrer un chiffre entre 1 et 3." + Console.RESET)
  } else {
    choice match {
      case "1" => displayTopAndLowestAirports()
      case "2" => displayRunwayTypes()
      case "3" => displayTopRunwayLatitudes()
    }
  }
}

// Report : Top 10 countries with the highest and lowest number of airports
def displayTopAndLowestAirports(): Unit = {
  println(Console.CYAN + "\n--- Top 10 pays avec le plus grand et le plus petit nombre d'aéroports ---" + Console.RESET)
  val (topCountries, lowestCountries) = Storage.reportTopAndLowestAirports()
  println(Console.GREEN + "Pays avec le plus grand nombre d'aéroports :" + Console.RESET)
  paginateResults(topCountries)
  println(Console.GREEN + "\nPays avec le plus petit nombre d'aéroports :" + Console.RESET)
  paginateResults(lowestCountries)
}

// Report : Type of runways per country
def displayRunwayTypes(): Unit = {
  println(Console.CYAN + "\n--- Types de pistes par pays ---" + Console.RESET)
  val runwayTypes = Storage.reportRunwayTypes()
  paginateResults(runwayTypes)
}

// Report : Top 10 most common runway latitudes
def displayTopRunwayLatitudes(): Unit = {
  println(Console.CYAN + "\n--- Top 10 des latitudes de pistes les plus courantes ---" + Console.RESET)
  val latitudes = Storage.reportTopRunwayLatitudes()
  paginateResults(latitudes)
}

// Pagination des résultats
def paginateResults(results: List[String]): Unit = {
  val pageSize = 10
  var currentPage = 0

  while (currentPage * pageSize < results.length) {
    val page = results.slice(currentPage * pageSize, (currentPage + 1) * pageSize)
    page.foreach(println)

    if ((currentPage + 1) * pageSize < results.length) {
      println(Console.YELLOW + "\nAppuyez sur 'n' pour la page suivante ou 'q' pour quitter." + Console.RESET)
      val choice = scala.io.StdIn.readLine().trim.toLowerCase
      if (choice == "q") return
      if (choice == "n") currentPage += 1
    } else {
      println(Console.GREEN + "\nFin des résultats." + Console.RESET)
      return
    }
  }
}



