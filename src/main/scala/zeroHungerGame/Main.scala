package zeroHungerGame

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.scene.image.Image

/* 
The entry point for the GUI system application: 
      - Initialisation of window for the Menu 
 */
object Main extends JFXApp3 {
      // A - Application Start Up
      override def start(): Unit = {
            println("Application starting ... ")

            // [1] Setup the primary stage: 
            try {
                  stage = new JFXApp3.PrimaryStage {
                        title = "NutriQuest - Supporting the UNSDG 2"
                        width = 1024 
                        height = 768
                        resizable = false
                        scene = new Scene {
                              // TO BE ADDED: Inclusion of the CSS stylesheet here later on 

                        }
                        
                        try {
                              // TO BE COMPLETED: 
                              // val iconPath // Need to work on this later on 
                              // icons += new Image(iconPath)
                        } catch {
                              case e: Exception => 
                                    println(s"Warning: Could not load application icon: ${e.getMessage}")
                        }
                  }

                  // Initiation of Menu (Initial Screen)
                  println("Preparing to launch Project NutriQuest ... ")
                  // SceneManager method

                  println("Application System UI Initialised")                  
            }catch{ 
                  case e: Exception => 
                        print(s"Error in application initialisation: ${e.getMessage}")
                        e.printStackTrace()
            }
      }

      // B - Application shutdown 
      override def stopApp(): Unit = {
            println("Application System UI shutting down ... ")
            // TO BE ADDED: Clean up application resources
      }
}