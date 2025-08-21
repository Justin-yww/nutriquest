package zeroHungerGame

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.scene.image.Image

import zeroHungerGame.core.{Routes, SceneManager}
import zeroHungerGame.util.{ResourceLoader, AudioService}

/* 
The entry point for the GUI system application: 
      - Initialisation of window for the Menu 
 */
object Main extends JFXApp3 {
      // A - Application Start Up
      override def start(): Unit = {
            println("Application starting ... ")

            // Initialisation of resource loader for assets
            try {
                  ResourceLoader.init()
            } catch {
                  case e: Exception =>
                        println(s"Error initializing resources: ${e.getMessage}")
                        e.printStackTrace()
            }

            // TO INCLUDE AUDIO INTO GUI SYSTEM 
            try {
                  AudioService.init()
            } catch {
                  case e: Exception =>
                  println(s"Error initializing audio: ${e.getMessage}")
                  e.printStackTrace()
            }

            // [1] Setup the primary stage: 
            try {
                  stage = new JFXApp3.PrimaryStage {
                        title = "In Support of UNSDG 2: Zero Hunger"
                        width = 1024 
                        height = 768
                        resizable = false
                        scene = new Scene {
                              // Only add stylesheet if a valid path is returned
                              val cssPath = ResourceLoader.loadCssPath("style.css")
                              if (cssPath.nonEmpty) {
                                    stylesheets = List(cssPath)
                              }

                        }
                        // Set Application Icon 
                        try {
                              val iconPath = ResourceLoader.loadImagePath("app_icon.png")
                              icons += new Image(iconPath)
                        } catch {
                              case e: Exception => 
                                    println(s"Warning: Could not load application icon: ${e.getMessage}")
                        }

                        onCloseRequest = _ => {
                              stopApp()
                        }
                  }

                  val sceneManager = SceneManager(stage)

                  // Initiation of Menu (Initial Screen)
                  println("Preparing to launch Project NutriQuest ... ")
                  sceneManager.goTo(Routes.Menu)

                  println("Application System UI Initialised")
            } catch {
                  case e: Exception =>
                        print(s"Error in application initialisation: ${e.getMessage}")
                        e.printStackTrace()
            }
      }

      // B - Application shutdown 
      override def stopApp(): Unit = {
            println("Application System UI shutting down ... ")
            
            // TURN OFF AUDIO
            try {
                  AudioService.shutdown()
            } catch {
                  case e: Exception =>
                  println(s"Error shutting down audio: ${e.getMessage}")
            }

            ResourceLoader.clearCaches()
      }
}