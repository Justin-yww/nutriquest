package zeroHungerGame

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane
import scalafx.geometry.Insets
import scalafx.stage.Stage

/** NutriQuest - A PRG 2104 Project (ScalaFX 21) */
object Main extends JFXApp3 {
  override def start(): Unit = {
      val root = SceneManager.load()
      
      stage = new Stage {
            title = "🪴 NutriQuest - A PRG 2104 Project"
            
            width = 1024
            height = 768
            resizable = true

      // Initialisation of scene management
      scene = new Scene {
        root = new BorderPane {
          padding = Insets(12)
          // Load the first screen of GUI System
          center = new Label("Welcome to NutriQuest! 🧑🏻‍🌾")
        }
      }
      // Stage is shown automatically when using JFXApp3

    }
  }
}