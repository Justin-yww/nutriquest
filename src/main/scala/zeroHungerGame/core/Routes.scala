package zeroHungerGame.core

sealed trait Screen {
      def fxmlPath: Option[String] 
      def title: String
}

object Routes { 
      // NOTE: Additional Screens can be added here
      // [1] Main Menu 
      case object Menu extends Screen {
            override def fxmlPath: Option[String] = Some("/zeroHungerGame/view/Menu.fxml")
            override def title: String = "Main Menu"
      }

      // [2] Educational Info Display Screen
      case class EduVillage(currentPage: Int = 0) extends Screen { 
            override def fxmlPath: Option[String] = Some("/zeroHungerGame/view/Education.fxml")
            override def title: String = "Sproutfiled - The Nutrition within the rural context"
      }

}