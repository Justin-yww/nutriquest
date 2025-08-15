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
            
            if (addToStack && navigationStack.nonEmpty) {
                  navigationStack.push(navigationStack.top)
            }

            try {
                  val content = screenCache.getOrElseUpdate(screen.id, loadScreen(screen))

                  // TO BE ADDED: Add the CSS to the loaded content

                  rootContainer.center = content 

                  // Updating the navigation stack
                  if (navigationStack.isEmpty || navigationStack.top != screen) {
                        navigationStack.push(screen)
                  }

                  if (screen == Routes.Result) {
                        // TO BE ADDED: Logic to handle result screen
                  }
            }catch {
                  case e: Exception =>
                        println(s"Error loading screen ${screen.id}: ${e.getMessage}")
                        e.printStackTrace()

                  // DISPLAY: Error message
                  val errorPane = new BorderPane()
                  val errorLabel = new Label(s"Error loading screen: ${e.getMessage}")
                  errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px")
                  errorPane.setCenter(errorLabel)

                  // TO BE ADDED: Add the CSS to the loaded content

                  rootContainer.center = errorPane

                  null
            }

      }

      private def applyCssToPane(pane: Pane): Unit = {
            // TO BE ADDED: Logic to apply CSS to the pane
            
      }
}

object SceneManager {
      // Singleton instance
      private var instance: Option[SceneManager] = None

      def apply(stage: Stage = null): SceneManager = {
            instance match {
                  case Some(manager) => 
                        manager
                  case None if stage != null => 
                        val newManager = new SceneManager(stage)
                        instance = Some(newManager)
                        newManager
                  case _ => 
                        throw new IllegalStateException("SceneManager must be initialized with a Stage first")
            }
      }
}

