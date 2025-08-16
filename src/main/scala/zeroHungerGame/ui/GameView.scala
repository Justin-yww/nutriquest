package zeroHungerGame.ui

import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout.{BorderPane, HBox, Pane, StackPane, VBox, Priority}
import scalafx.scene.control.{Button, Label, ProgressBar, ScrollPane}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.TextAlignment
import scalafx.animation.PauseTransition
import scalafx.util.Duration
import scala.collection.mutable

import zeroHungerGame.core.{Routes, SceneManager}
import zeroHungerGame.game.{GameEngine, GameEvent}
import zeroHungerGame.model.{Character, FoodItem}
import zeroHungerGame.util.{FxEffects, ResourceLoader}

class GameView(gameEngine: GameEngine, sceneManager: SceneManager) {
      
      // MAIN ROOT CONTAINER 
      val root: BorderPane = new BorderPane()

      // GAME CONTAINER
      private val gameBoard = new Pane {
            styleClass += "game-board"
      }

      // CHARACTER CONTAINER
      private val characterHBox = new HBox {
            spacing = 12
            alignment = Pos.CenterLeft
            padding = Insets(5) 
            minHeight = 300 
      }

      // FOOD ITEM CONTAINER
      private val foodHBox = new HBox {
            spacing = 12 
            alignment = Pos.CenterLeft
            padding = Insets(5) 
            minHeight = 120 
      }

      // MAIN CONTAINER - Game Content 
      private val gameContentBox = new VBox {
            spacing = 12 
            alignment = Pos.Center
            padding = Insets(5, 10, 5, 10)
      }

      // SERVICES 
      private val dragDropService = new zeroHungerGame.game.DragDropService()
      private val matchedCharacterBoxes = mutable.Map[Character, VBox]()

      // COMPONENTS 
      private val hud = createHUD()
      private val backgroundImageView = new ImageView {
            fitWidth = 1024
            fitHeight = 768
            preserveRatio = false
            
            val imagePath = ResourceLoader.loadImagePath(
                  if (gameEngine.mode == "village") "backgrounds/village.png" 
                  else "backgrounds/urban.png"
            )
            image = new Image(imagePath)
            image.get().errorProperty().addListener((_, _, error) => {
                  if (error) {
                  image = new Image(ResourceLoader.loadImagePath("placeholder.png"))
                  }
            })
      }

      // INITIALISATION OF THE VIEW
      initialize()

      // METHOD: To initialise view 
      private def initialize(): Unit = {

            // Load in the styling 
            root.stylesheets = List(ResourceLoader.loadCssPath("style.css"))
            root.styleClass += "game-view"
            
            // Set up layout
            root.top = hud

            // Setup character and food items displays
            setupCharacters()
            setupFoods()
            
            // Help register game events
            gameEngine.addEventListener(handleGameEvent)
            
            // Set up game controls
            setupGameControls()
      }

      // METHOD: Create the main game area
      private def createGameArea(): Pane = {
            // Stack the UI layers
            val stackPane = new StackPane()
            
            gameContentBox.children = Seq(
                  new Label("Characters - Match food to their nutritional needs") {
                        styleClass += "section-header"
                        style = "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.7); -fx-padding: 6px 12px; -fx-background-radius: 4px; -fx-margin-top: 5px;"
                  },
                  new Label("Available Foods - Drag to match characters") {
                        styleClass += "section-header"
                        style = "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.7); -fx-padding: 6px 12px; -fx-background-radius: 4px;"
                  }
            )
            
            stackPane.children = Seq(
                  backgroundImageView,
                  gameContentBox
            )
            
            stackPane
      }

      // METHOD: Creation of HUD
      private def createHUD(): Pane = {
            val titleLabel = new Label(gameEngine.levelConfig.name) {
                  styleClass += "game-title"
            }
            
            // [1] SCORE BOX
            val scoreBox = new HBox {
                  spacing = 10
                  alignment = Pos.CenterLeft
                  children = List(
                        new Label("Score:") {
                              styleClass += "score-label"
                              style = "-fx-text-fill: white; -fx-font-weight: normal;"
                        },
                        new Label {
                              text <== gameEngine.scoreProperty.asString
                              styleClass += "score-value"
                              style = "-fx-text-fill: #f59e0b; -fx-font-weight: bold; -fx-font-size: 15px;"
                        }
                  )
            }
            
            // [2] TIMER BOX
            val timerBox = new HBox {
                  spacing = 10
                  alignment = Pos.CenterLeft
                  children = List(
                        new Label("Time:") {
                              styleClass += "timer-label"
                              style = "-fx-text-fill: white; -fx-font-weight: normal;"
                        },
                        new Label {
                              text <== gameEngine.timeRemainingProperty.asString
                              styleClass += "timer-value"
                              style = "-fx-text-fill: #f59e0b; -fx-font-weight: bold; -fx-font-size: 15px;"
                        }
                  )
            }
            
            // [3] PAUSE BUTTON
            val pauseButton = new Button("Pause") {
                  styleClass += "control-button"
                  onAction = _ => {
                        if (gameEngine.gameStatus == "running") {
                              gameEngine.pause()
                              text = "Resume"
                        } else if (gameEngine.gameStatus == "paused") {
                              gameEngine.resume()
                              text = "Pause"
                        }
                  }
            }
            
            // [4] RETURN TO MENU BUTTON
            val menuButton = new Button("Menu") {
                  styleClass += "control-button"
                  onAction = _ => {

                        gameEngine.stop()
                        gameEngine.reset()

                        sceneManager.clearGameScreensAndGoToMenu()
                  }
            }
            
            // BUTTON CONTAINER
            val controlBox = new HBox {
                  spacing = 10
                  alignment = Pos.CenterRight
                  children = List(pauseButton, menuButton)
            }
            
            // (1) SCORE AND (2) TIMER - Spacing
            val statsBox = new HBox {
                  spacing = 30 
                  alignment = Pos.CenterLeft
                  children = List(scoreBox, timerBox)
            }
            
            // TOP HUD BAR 
            val topBar = new HBox {
                  spacing = 20
                  padding = Insets(8, 15, 8, 15)  
                  alignment = Pos.CenterLeft
                  styleClass += "hud-bar"
                  children = List(
                        statsBox,  
                        new Pane {
                              hgrow = Priority.Always 
                        },
                        controlBox
                  )
            }
            
            // COMPLETE HUD 
            val hudContainer = new VBox {
                  spacing = 0 
                  children = List(
                        titleLabel,
                        topBar
                  )
            }
            hudContainer
      }

      // METHOD: Prepare Game Characters 
      private def setupCharacters(): Unit = {
            characterHBox.children.clear()
            resetCharacterStyling()
            matchedCharacterBoxes.clear()
            
            // DEBUG
            println(s"Setting up ${gameEngine.characters.size} characters in scrollable container")
            
            val totalCharacters = gameEngine.characters.size
            val minContentWidth = totalCharacters * 270 
            characterHBox.minWidth = math.max(minContentWidth, 800)
            
            println(s"Character container setup complete with ${totalCharacters} characters")            
      }

      // METHOD: 
      private def createCharacterBox(character: Character): Pane = {
      // CONTAINER FOR CHARACTER BOXES
      val box = new VBox {
            spacing = 5 
            alignment = Pos.Center
            styleClass += "character-box"
            id = character.name 
            prefWidth = 230 
            maxWidth = 230
            minWidth = 230
            minHeight = 280 
            maxHeight = 280
      }
      
      matchedCharacterBoxes(character) = box
      
      // COMPLETION STATUS
      val completionBadge = new Label("✓ COMPLETED") {
            styleClass += "character-completed-badge"
            visible = false
            managed = false
      }
      
      // CHARACTER IMAGE 
      val imageView = new ImageView {
            image = character.image
            fitWidth = 80 
            fitHeight = 80
            preserveRatio = true
            style = "-fx-background-color: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 1);"
      }
      
      // CHARACTER NAME 
      val nameLabel = new Label(character.name) {
            styleClass += "character-name"
            style = "-fx-font-size: 15px; -fx-font-weight: bold;" 
            wrapText = true
            maxWidth = 220
            textAlignment = TextAlignment.Center
      }
      
      // CHARACTER ROLE 
      val roleLabel = new Label(character.role) {
            styleClass += "character-role"
            style = "-fx-font-size: 13px; -fx-font-style: italic;"
            wrapText = true
            maxWidth = 220
            textAlignment = TextAlignment.Center
      }
      
      // CHARACTER PROMPT (HINTS)
      val promptText = new Label(character.nutritionPrompt) {
            wrapText = true
            maxWidth = 210
            style = "-fx-font-size: 11px; -fx-text-fill: #2e7d32; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 4px; -fx-background-radius: 4px;"
            textAlignment = TextAlignment.Center
      }
      

      val promptScroll = new ScrollPane {
            content = promptText
            fitToWidth = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.Never
            vbarPolicy = ScrollPane.ScrollBarPolicy.AsNeeded
            prefHeight = 50 // Reduced height
            maxHeight = 50
            style = "-fx-background-color: transparent; -fx-background: transparent;"
      }
      
      // COMPONENTS OF CHARACTER BOX
      box.children = List(
            completionBadge,
            imageView,
            nameLabel,
            roleLabel,
            promptScroll
      )
      
      // SERVICE - Drag n Drop 
      dragDropService.registerDropTarget(
            box, 
            character, 
            (food, char) => gameEngine.matchFoodToCharacter(char)
      )
      
      // EFFECTS 
      box.onMouseEntered = _ => {
            if (!isCharacterMatched(character)) {
            FxEffects.applyGlowEffect(box)
            }
      }
      box.onMouseExited = _ => {
            if (!isCharacterMatched(character)) {
            FxEffects.removeEffect(box)
            }
      }
      
      box
      }

      // METHOD: Check if character is matched
      private def isCharacterMatched(character: Character): Boolean = {
            matchedCharacterBoxes.get(character).exists(_.getStyleClass.contains("character-box-matched"))
      }

      // METHOD: Apply matched styling
      private def applyMatchedStyling(character: Character): Unit = {
            matchedCharacterBoxes.get(character).foreach { box =>
                  box.styleClass.remove("character-box")
                  box.styleClass += "character-box-matched"
                  
                  box.children.headOption.foreach { badge =>
                  badge.visible = true
                  badge.managed = true
                  }
                  
                  box.onMouseEntered = null
                  box.onMouseExited = null
                  
                  println(s"Applied persistent matched styling to ${character.name}")
            }
      }  

      // METHOD: Reset all character styling (PREPARATION FOR NEW GAME)
      private def resetCharacterStyling(): Unit = {
            matchedCharacterBoxes.values.foreach { box =>
                  box.styleClass.clear()
                  box.styleClass += "character-box"
                  
                  box.children.headOption.foreach { badge =>
                  badge.visible = false
                  badge.managed = false
                  }
                  
                  box.onMouseEntered = _ => FxEffects.applyGlowEffect(box)
                  box.onMouseExited = _ => FxEffects.removeEffect(box)
            }
            
            println("Reset all character styling to default state")
      }

      

}