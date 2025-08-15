package zeroHungerGame.core

import scala.collection.mutable
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.scene.layout.{BorderPane, Pane}
import scalafx.scene.control.Label
import javafx.fxml.FXMLLoader
import javafx.scene.Parent

import zeroHungerGame.core.Routes.Screen

// PURPOSE: Manage scene transition and loading of FXML files
class SceneManager private (stage: Stage) {
      // Stack - Manage the navigation history 
      private val navigationStack = mutable.Stack[Screen]()

      // Cache of loaded screen - Prevents reloading 
      private val screenCache = mutable.Map[Screen, Pane]()

      // Root container - to hold all scenes
      private val root = new BorderPane()
      
      // Root scene - Set up the CSS for scenes
      private val mainScene = new Scene(rootContainer) { 
            // TO BE ADDED: Css file loading method 
            
      }

      stage.scene = mainScene

      def goTo(screen:Screen, addToStack: Boolean = true): Any = { 
            println(s"Navigating to screen: ${screen.id}")
            
            if (addToStack) {
                  // TO BE ADDED: Logic to manage scene stack
            }

      }
}