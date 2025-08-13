object Main {
      def main (args: Array[String]): Unit = {
            
      }
}

class MainApp extends Application { 
      override def start(primaryStage: Stage): Unit = {
            // [1] Area with the window for the entire GUI System 
            primaryStage.setTitle("Zero Hunger Game")
            primaryStage.setScene(new Scene(new Group(), 800, 600))
            primaryStage.show()

            // [2] Initialisation of scene management 
            
            // [3] Load the first screen of GUI System 

            // [4] Displaying the window 
      }

}