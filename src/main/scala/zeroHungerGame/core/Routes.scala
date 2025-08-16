package zeroHungerGame.core

// PURPOSE: Define fxml paths for the different screens in the application
object Routes {
      // NOTE: Trait will be used for all screens 
      sealed trait Screen {
            def fxmlPath: Option[String] = None
            def id: String // Allow better control over screen identification and sequencing
      }

      // [1] Main Menu Screen
      case object Menu extends Screen {
            override def fxmlPath: Option[String] = Some("view/Menu.fxml")
            override def id: String = "menu"
      }

      // [2] Village Mode - Education Section 
      final case class VillageEducation(pageNumber: Int) extends Screen{
            override val fxmlPath  = Some("view/Education.fxml")
            override val id        = s"village-edu-$pageNumber"

            // This will be the total number of pages for the educational section [Village]
            private val totalPages  = 5
            def isLastPage: Boolean = pageNumber >= totalPages
            def nextPage: Int       = if (isLastPage) pageNumber else pageNumber + 1
            def prevPage: Int       = if (pageNumber <= 1) pageNumber else pageNumber - 1
      }


      // [3] Urban Mode - Eduaciton Section 
      final case class UrbanEducation(pageNumber: Int) extends Screen {
            override val fxmlPath  = Some("view/Education.fxml")
            override val id        = s"urban-edu-$pageNumber"

            // This will be the total number of pages for the educational section [Urban]
            private val totalPages  = 5
            def isLastPage: Boolean = pageNumber >= totalPages
            def nextPage: Int       = if (isLastPage) pageNumber else pageNumber + 1
            def prevPage: Int       = if (pageNumber <= 1) pageNumber else pageNumber - 1
      }

      // [4] Village Mode - Game Section
      case object GameVillage extends Screen { 
            override val id = "game-village"
      }

      // [5] Urban Mode - Game Section
      case object GameUrban extends Screen {
            override val id = "game-urban"
      }

      // [6] Result 
      case object Result extends Screen {
            override val fxmlPath  = Some("view/Result.fxml")
            override def id        = "result"
      }

}