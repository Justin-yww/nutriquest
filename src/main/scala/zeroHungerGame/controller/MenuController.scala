package zeroHungerGame.controller

import scalafx.Includes._
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, Tooltip}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{Pane, VBox, HBox}
import java.net.URL
import java.util.ResourceBundle

import zeroHungerGame.util.{FxEffects, ResourceLoader, AudioService}
import zeroHungerGame.core.{Routes, SceneManager}
import scalafx.scene.input.KeyCode.P

// PURPOSE: Helps control the menu 
class MenuController(sceneManager: SceneManager) extends Initializable {
      // FXML fields
      @FXML private var titleLabel: Label = _
      @FXML private var menuContainer: VBox = _
      @FXML private var logoImageView: ImageView = _
      @FXML private var backgroundImageView: ImageView = _
      @FXML private var villageButton: Button = _
      @FXML private var urbanButton: Button = _
      @FXML private var exitButton: Button = _
      @FXML private var helpButton: Button = _
      @FXML private var creditsButton: Button = _
      @FXML private var audioToggleButton: Button = _ 
      @FXML private var audioControlsContainer: HBox = _ 

      // TRACK MUTE STATE
      private var isMuted: Boolean = false

      override def initialize(location: URL, resources: ResourceBundle): Unit = {
            println("MenuController initializing...")

            // PURPOSE: Load the background image
            try {
                  loadBackgroundImage()
            } catch {
                  case e: Exception =>
                        println(s"Error loading background image: ${e.getMessage}")
                        e.printStackTrace()
            }

            // PURPOSE: Load the logo image
            try {
                  val logoUrl = ResourceLoader.loadImagePath("logo.png")
                  println(s"Loading logo image from: $logoUrl")
                  logoImageView.setImage(new Image(logoUrl))
            }catch { 
                  case e: Exception => 
                        println(s"Error loading logo image: ${e.getMessage}")
                        e.printStackTrace()
            }

            // PURPOSE: Setup buttons
            try {
                  setupButtons()
            }catch {
                  case e: Exception =>
                        println(s"Error setting up buttons: ${e.getMessage}")
                        e.printStackTrace()
            }

            // PURPOSE: Setup Top Menu Bar Buttons
            setupHelpButton()
            setupCreditsButton()
            setupAudioToggle()
      }

      // METHOD: Setup help button 
      private def setupHelpButton(): Unit = {
            if (helpButton != null) {
                  helpButton.setTooltip(new Tooltip("Show app guidance"))
                  helpButton.setOnAction(_ => {
                        showHelpOverlay()
                  })
                  println("Help button initialised")
            } else {
                  println("WARNING: helpButton is null!")
            }
      }

      // METHOD: Show help overlay 
      private def showHelpOverlay(): Unit = { 
            val alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION)
            alert.setTitle("How does NutriQuest work")
            alert.setHeaderText("NutriQuest - Application Instructions")

            val helpContent = 
                  "NutriQuest as the name suggest is about a quest for healthy nutrition:\n\n" + 
                  "1. Users will be given a choice to choose between both the village and urban modes.\n" + 
                  "2. Users will be guided first with nutrition facts in the context they have chosen.\n" + 
                  "3. Based on the education section, users will engage in a quest to match food items with chracters based on their nutritional needs.\n" + 
                  "4. The speed of the user making correct mataches will lead to higher scores.\n" + 
                  "5. There will be 120 seconds for the village mode and 60 seconds for the urban mode.\n" + 
                  "6. At the end of the game, a user has three options; try again, try new mode, or return to menu.\n" + 
                  "Controls:\n" +
                  "- Drag and drop food items to characters\n" +
                  "- Use scroll bars to see all characters and foods\n" +
                  "- Pause button to pause the game"
            
            val textArea = new javafx.scene.control.TextArea(helpContent)
            textArea.setEditable(false)
            textArea.setWrapText(true)
            textArea.setPrefHeight(400)
            textArea.setPrefWidth(550)

            alert.getDialogPane.setContent(textArea)
            alert.getDialogPane.setPrefWidth(600)

            val cssPath = ResourceLoader.loadCssPath("style.css")
            if (cssPath.nonEmpty) {
                  alert.getDialogPane.getStylesheets.add(cssPath)
            } 

            alert.showAndWait()
      }

      // METHOD: Setup credits button 
      private def setupCreditsButton(): Unit = {
            if (creditsButton != null) {
                  creditsButton.setTooltip(new Tooltip("View app credits"))
                  creditsButton.setOnAction(_ => {
                        showCreditsOverlay()
                  })
                  println("Credits button initialised")
            } else {
                  println("WARNING: creditsButton is null!")
            }
      }

      // METHOD: Show credits overlay 
      private def showCreditsOverlay(): Unit = {
            val alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION)
            alert.setTitle("App Credits")
            alert.setHeaderText("NutriQuest - In Support of UNSDG 2")

            val creditsContent = 
                  "Developed for PRG 2104 Object Oriented Programming Course\n" + 
                  "Sunway University Malaysia: https://www.sunway.edu.my\n\n" +
                  "Credits:\n\n" + 
                  "1. Developed by Justin Yong Wenn Weii (Student ID: 18119677)\n\n" + 
                  "2. Background Images: Taken from Canva\n" + 
                  "Link: https://www.canva.com/design/DAGuPqns9cw/KfW9l09ESmPBpRX2YM24gg/edit?utm_content=DAGuPqns9cw&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton\n\n" +
                  "3. Background Music: Schwarzenegger Belonio\n" + 
                  "Link: https://migfus.site/\n\n" + 
                  "4. Logo and Character Images: Designed by Justin Yong with Canva\n" + 
                  "Link: https://www.canva.com/design/DAGwsWrAu1M/zA5vrzYh05v50_paDbgNUA/edit?utm_content=DAGwsWrAu1M&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton\n\n" + 
                  "5. Food Item Designs: Designed by Justin Yong with Canva\n" + 
                  "Link: https://www.canva.com/design/DAGwsWeYC00/qXgx2VXdPjy6yyJEwK81lA/edit?utm_content=DAGwsWeYC00&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton\n\n" + 
                  "Special thanks to all open-source libraries used in this project."

            val textArea = new javafx.scene.control.TextArea(creditsContent)
            textArea.setEditable(false)
            textArea.setWrapText(true)
            textArea.setPrefHeight(400)
            textArea.setPrefWidth(550)

            alert.getDialogPane.setContent(textArea)
            alert.getDialogPane.setPrefWidth(600)

            val cssPath = ResourceLoader.loadCssPath("style.css")
            if (cssPath.nonEmpty) {
                  alert.getDialogPane.getStylesheets.add(cssPath)
            } 

            alert.showAndWait()
      }

      // METHOD: Setup audio toggle button 
      private def setupAudioToggle(): Unit = {
            if (audioToggleButton != null) {
                  
                  audioToggleButton.setTooltip(new Tooltip("Toggle background music on/off"))
                  audioToggleButton.setOnAction(_ => {
                        isMuted = AudioService.toggleMute()
                  
                        if (isMuted) {
                              audioToggleButton.setText("🔇 Music Off")
                        } else {
                              audioToggleButton.setText("🔊 Music On")
                        }
                  })
                  
                  println("Audio toggle button initialized")
            } else {
                  println("WARNING: audioToggleButton is null!")
            }
      }

      // METHOD: Load and set the background image
      private def loadBackgroundImage(): Unit = {
            try {
                  val backgroundPath = ResourceLoader.loadImagePath("backgrounds/menu_screen.png")
                  println(s"Loading background image from: $backgroundPath")

                  if (backgroundImageView != null){
                        val backgroundImage = new Image(backgroundPath)
                        backgroundImageView.setImage(backgroundImage)

                        backgroundImage.errorProperty().addListener((_, _, error) => {
                              if (error) {
                                    println("Error loading background image, using fallback")
                                    loadFallbackBackground()
                              }else{
                                    println("Background image loaded successfully")
                              }
                        })

                        backgroundImage.progressProperty().addListener((_, _, progress) => {
                              if (progress.doubleValue() >= 1.0) {
                                    println(s"Background image dimensions: ${backgroundImage.getWidth} x ${backgroundImage.getHeight}")
                              }
                        })
                  } else {
                        println("WARNING: backgroundImageView is null!")
                  }
            } catch {
            case e: Exception =>
                  println(s"Error loading background image: ${e.getMessage}")
                  e.printStackTrace()
                  loadFallbackBackground()
            }
      }

      // METHOD: Load and set the fallback background image
      private def loadFallbackBackground(): Unit = {
            try {
                  if (backgroundImageView != null) {
                        val placeholderPath = ResourceLoader.loadImagePath("placeholder.png")
                        val placeholderImage = new Image(placeholderPath)
                        backgroundImageView.setImage(placeholderImage)
                        println("Using placeholder as background fallback")
                  }
      } catch {
            case e: Exception =>
                  println(s"Error loading fallback background image: ${e.getMessage}")
            }
      }

      // METHOD: Manage the button setup and redirection 
      private def setupButtons(): Unit = {
            println("Setting up menu buttons ...")

            // VILLAGE MODE BUTTOn 
            if (villageButton != null) {
                  villageButton.setOnAction(event => {
                        println("Village mode button clicked")
                        sceneManager.startFreshGame(Routes.VillageEducation(1))
                  })
                  villageButton.setTooltip(new Tooltip(
                        "Play in the village setting - match local farm foods to village characters' nutritional needs"
                  ))
            } else {
                  println("WARNING: villageButton is null!")
            }

            // URBAN MODE BUTTON 
            if (urbanButton != null) {
                  urbanButton.setOnAction(event => {
                        println("Urban mode button clicked")
                        sceneManager.startFreshGame(Routes.UrbanEducation(1))
                  })
                  urbanButton.setTooltip(new Tooltip(
                  "Play in the urban setting - match city-available foods to urban characters' nutritional needs"
                  ))
            } else {
                  println("WARNING: urbanButton is null!")
            }

            // EXIT BUTTON 
            if (exitButton != null) {
                  exitButton.setOnAction(event => {
                        println("Exit button clicked")
                        scala.sys.exit(0)
                  })
            } else {
                  println("WARNING: exitButton is null!")
            }

            println("Menu buttons setup complete")
      }
}