package zeroHungerGame.core

import scala.collection.mutable
import scalafx.scene.layout.{BorderPane, Pane}
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.stage.Stage

import scalafx.scene.control.Label
import javafx.fxml.FXMLLoader
import javafx.scene.Parent

import zeroHungerGame.controller.{MenuController, EducationController, ResultController}
import zeroHungerGame.core.Routes.Screen
import zeroHungerGame.ui.GameView
import zeroHungerGame.util.ResourceLoader
import zeroHungerGame.game.GameEngine

// PURPOSE: Manage scene transition and loading of FXML files
class SceneManager private (stage: Stage) {

      // Stack - Manage the navigation history 
      private val navigationStack = mutable.Stack[Screen]()

      // Cache of loaded screen - Prevents reloading 
      private val screenCache = mutable.Map[String, Pane]()

      // Root container - to hold all scenes
      private val rootContainer = new BorderPane()

      // Root scene - Set up the CSS for scenes
      private val mainScene = new Scene(rootContainer) { 
            val cssPath = ResourceLoader.loadCssPath("style.css")
            if (cssPath.nonEmpty) {
                  stylesheets = List(cssPath)
                  println(s"Applied CSS stylesheet: $cssPath")
            } else {
                  println("Warning: CSS stylesheet not found or empty")
            }
      }

      stage.scene = mainScene

      // PURPOSE: To navigate to a specified screen
      def goTo(screen: Screen, addToStack: Boolean = true): Any = { 
            println(s"Navigating to screen: ${screen.id}")
            
            if (addToStack && navigationStack.nonEmpty) {
                  navigationStack.push(navigationStack.top)
            }

            try {
                  val content = screenCache.getOrElseUpdate(screen.id, loadScreen(screen))

                  // Apply CSS 
                  applyCssToPane(content)

                  rootContainer.center = content 

                  // Updating the navigation stack
                  if (navigationStack.isEmpty || navigationStack.top != screen) {
                        navigationStack.push(screen)
                  }

                  if (screen == Routes.Result) {
                        val userData = content.getUserData
                        if (userData != null && userData.isInstanceOf[FXMLLoader]) {
                              val loader = userData.asInstanceOf[FXMLLoader]
                              return loader.getController
                        } else {
                              return null
                        }
                  } else {
                        return null  
                  }
            
            } catch {
                  case e: Exception =>
                        println(s"Error loading screen ${screen.id}: ${e.getMessage}")
                        e.printStackTrace()

                        // DISPLAY: Error message
                        val errorPane = new BorderPane()
                        val errorLabel = new Label(s"Error loading screen: ${e.getMessage}")
                        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px")
                        errorPane.setCenter(errorLabel)

                        // Apply CSS 
                        applyCssToPane(errorPane)

                        rootContainer.center = errorPane

                        null
            }

      }

      // PURPOSE: To apply CSS styles to the pane (Includes the dynamic applied to the other content)
      private def applyCssToPane(pane: Pane): Unit = {
            try {
                  val cssPath = ResourceLoader.loadCssPath("style.css")
                  println(s"CSS path resolved to: $cssPath")

                  if (cssPath.nonEmpty && !cssPath.startsWith("ERROR")){ 
                        // Clear initial stylesheet 
                        pane.stylesheets.clear()

                        // Add the CSS stylesheet to the pane
                        pane.stylesheets.add(cssPath)

                        // Add the CSS to the main scene too 
                        // TO NOTE: This shows inheritance 
                        val scene = stage.scene.value
                        if (scene != null && !scene.getStylesheets.contains(cssPath)) {
                              scene.getStylesheets.add(cssPath)
                              println(s"Applied CSS stylesheet to main scene: $cssPath")
                        }

                        println(s"Applied CSS stylesheet to pane: ${pane.getClass.getSimpleName} with ${pane.stylesheets.size} stylesheets")

                        // DEBUG 
                        pane.stylesheets.foreach(sheet => print(s" Stylesheet: $sheet"))
                  } else {
                        println(s"Warning: CSS path is empty or invalid: '$cssPath'")
                  }
            } catch { 
                  case e: Exception =>
                        println(s"Error applying CSS to pane: ${e.getMessage}")
                        e.printStackTrace()
            } 
      }

      // PURPOSE: To navigate to the previous screen in navigation history
      def pop(): Boolean = {
            if (navigationStack.size > 1) {
                  navigationStack.pop() 
                  val previousScreen = navigationStack.pop()
                  goTo(previousScreen, addToStack = false)
                  true
            } else {
                  println("No previous screen to navigate back to.")
                  false
            }
      }

      // PURPOSE: Clear the navigation history and go to a specified screen 
      def clearAndGoTo(screen: Screen): Unit = {
            navigationStack.clear()
            goTo(screen, addToStack = false)
      }

      // PURPOSE: Clear the navigation history and restart a game screen 
      // REASON: This will help reset all the game settings when a user has already played the game
      def clearAndRestart(screen: Screen): Unit = {
            navigationStack.clear()
            screenCache.remove(screen.id)
            goTo(screen, addToStack = false)
      }

      // PURPOSE: Clear game screen and navigate back to menu 
      def clearGameScreensAndGoToMenu(): Unit = {
            screenCache.remove(Routes.GameVillage.id)
            screenCache.remove(Routes.GameUrban.id)
            screenCache.remove(Routes.Result.id)

            navigationStack.clear()

            // Return to menu (Used for resetting game state)
            goTo(Routes.Menu, addToStack = false)
      }

      // PURPOSE: Start a complete new game (clear score and rest timer)
      def startFreshGame(screen: Screen): Unit = {
            screenCache.remove(screen.id)
            screenCache.remove(Routes.Result.id)

            goTo(screen, addToStack = true)
      }

      // PURPOSE: Load screen based on the type
      private def loadScreen(screen: Screen): Pane = {
            try {
                  val loadedPane = screen match { 
                        // SECTION 1: FXML-based
                        case s if s.fxmlPath.isDefined => 
                              loadFxmlScreen(s)

                        // SECTION 2: Scala-based
                        case Routes.GameVillage => 
                              println("Loading GameVillage screen")
                              val engine = new GameEngine("village")
                              new GameView(engine, this).root

                        case Routes.GameUrban => 
                              println("Loading GameUrban screen")
                              val engine = new GameEngine("urban")
                              new GameView(engine, this).root

                        case _ => 
                              println(s"No handler for screen type ${screen.id}, returning an empty pane")
                              new BorderPane()
                  }

                  // Apply CSS
                  applyCssToPane(loadedPane)

                  loadedPane
            }catch {
            case e: Exception =>
                  println(s"Error loading screen ${screen.id}: ${e.getMessage}")
                  e.printStackTrace()
                  
                  // Creation of a pane with the error message
                  val fallbackPane = new BorderPane()
                  val errorLabel = new Label(s"Error loading screen: ${e.getMessage}")
                  errorLabel.style = "-fx-text-fill: red; -fx-font-size: 16px;"
                  fallbackPane.center = errorLabel
                  
                  // Apply CSS 
                  applyCssToPane(fallbackPane)

                  fallbackPane
            }
      }

      // PURPOSE: Load up the FXML UI structure 
      private def loadFxmlScreen(screen: Screen): Pane = { 
            val resourcePath = screen.fxmlPath.get

            try {
                  val possiblePaths = List(
                        s"/zeroHungerGame/$resourcePath",   // Standard path
                        resourcePath,                       // Direct path
                        s"/$resourcePath"                   // Root-relative path
                  )

                  // Searching for the FXML file with a different path 
                  val url = possiblePaths.map(path => { 
                        println(s"Trying to load FXML from path: $path")
                        getClass.getResource(path)
                  }).find(_ != null).getOrElse {
                        throw new RuntimeException(s"Could not find FXML file at any of these paths: ${possiblePaths.mkString(", ")}")
                  }

                  println(s"Found FXML at: ${url.toString}")
                  val loader = new FXMLLoader(url)

                  // CONTROLLER MATCH:  MenuController , EducationController , ResultController
                  screen match {
                        case Routes.Menu => 
                              loader.setController(new MenuController(this))

                        case eduVillage: Routes.VillageEducation => 
                              val controller = new EducationController(this)
                              loader.setController(controller) 

                              // STEP 1 : Load the FXML
                              val root = loader.load[javafx.scene.layout.Pane]()

                              // STEP 2 : Apply CSS first to the FXML
                              applyCssToFxmlRoot(root)

                              // STEP 3 : Initialize the controller with the loaded root
                              controller.setModeAndPage("village",eduVillage.pageNumber)

                              // STEP 4 : Store loader in the root's user data
                              root.setUserData(loader)

                              println(s"Loaded VillageEducation FXML for page: ${eduVillage.pageNumber}")
                              return root

                        case eduUrban: Routes.UrbanEducation => 
                              val controller = new EducationController(this)
                              loader.setController(controller) 

                              // STEP 1 : Load the FXML
                              val root = loader.load[javafx.scene.layout.Pane]()

                              // STEP 2 : Apply CSS first to the FXML
                              applyCssToFxmlRoot(root)

                              // STEP 3 : Initialize the controller with the loaded root
                              controller.setModeAndPage("urban",eduUrban.pageNumber)

                              // STEP 4 : Store loader in the root's user data
                              root.setUserData(loader)

                              println(s"Loaded UrbanEducation FXML for page: ${eduUrban.pageNumber}")
                              return root
                        
                        case Routes.Result => 
                              loader.setController(new ResultController(this))
               
                        case _ => 
                        
                  }

                  val root = loader.load[javafx.scene.layout.Pane]()
                  
                  applyCssToFxmlRoot(root)

                  root.setUserData(loader) 

                  println(s"Loaded FXML for screen: ${screen.id}")
                  root
            } catch { 
                  case e: Exception => 
                        println(s"Error loading FXML for screen: ${screen.id}: ${e.getMessage}")
                        e.printStackTrace()

                        // Creation of a pane with the error message
                        val fallbackPane = new javafx.scene.layout.BorderPane()
                        val errorLabel = new javafx.scene.control.Label(s"Error loading screen: ${e.getMessage}")
                        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;")
                        fallbackPane.setCenter(errorLabel)

                        fallbackPane
            }
      }

      // PURPOSE: Apply CSS to FXML root elements
      private def applyCssToFxmlRoot(root: javafx.scene.layout.Pane): Unit = {
            try {
                  val cssPath = ResourceLoader.loadCssPath("style.css")
                  println(s"Applying CSS to FXML root: $cssPath")

                  if (cssPath.nonEmpty && !cssPath.startsWith("ERROR")){ 
                        if (!root.getStylesheets.contains(cssPath)) {
                              root.getStylesheets.add(cssPath)
                              println(s"Applied CSS to FXML root: $cssPath")
                        } 

                        val scene = stage.getScene
                        if (scene != null && !scene.getStylesheets.contains(cssPath)) {
                              scene.getStylesheets.add(cssPath)
                              println(s"Added CSS to main scene for FXML compatibility")
                        }

                        // DEBUG 
                        println(s"FXML root stylesheets count: ${root.getStylesheets.size()}")
                        for (i <- 0 until root.getStylesheets.size()) {
                              println(s" FXML Stylesheet $i: ${root.getStylesheets.get(i)}")
                        }

                        root.applyCss()
                  } else {
                        println(s"ERROR: Cannot apply CSS to FXML root - invalid path: $cssPath")
                  }
            } catch { 
            case e: Exception =>
                        println(s"Error applying CSS to FXML root: ${e.getMessage}")
                        e.printStackTrace()
            }
                        
                             
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

