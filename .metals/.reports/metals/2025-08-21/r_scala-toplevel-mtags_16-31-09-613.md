error id: file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/MenuController.scala:[2706..2713) in Input.VirtualFile("file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/MenuController.scala", "package zeroHungerGame.controller

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
            // Help Button
            // Credits Button
            setupAudioToggle()
      }

      // METHOD: Setup help button 
      private def 

      // METHOD: Setup credits button 

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
}")
file://<WORKSPACE>/file:<WORKSPACE>/src/main/scala/zeroHungerGame/controller/MenuController.scala
file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/MenuController.scala:77: error: expected identifier; obtained private
      private def setupAudioToggle(): Unit = {
      ^
#### Short summary: 

expected identifier; obtained private