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
import zeroHungerGame.controller.ResultController

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

      // CHARACTER CONTAINER - Scrollable
      private val characterScrollPane = new ScrollPane {
            content = characterHBox
            fitToHeight = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.AsNeeded
            vbarPolicy = ScrollPane.ScrollBarPolicy.Never
            styleClass += "character-container"
            prefHeight = 320
            maxHeight = 320
            style = "-fx-background-color: rgba(255, 255, 255, 0.3); -fx-padding: 5px; -fx-border-radius: 8px;"
      }

      // FOOD ITEM BOX
      private val foodHBox = new HBox {
            spacing = 12 
            alignment = Pos.CenterLeft
            padding = Insets(5) 
            minHeight = 120 
      }

      // FOOD ITEM BOX - Scrollable
      private val foodScrollPane = new ScrollPane {
            content = foodHBox
            fitToHeight = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.AsNeeded
            vbarPolicy = ScrollPane.ScrollBarPolicy.Never
            styleClass += "food-container"
            prefHeight = 140 
            maxHeight = 140
            style = "-fx-background-color: rgba(255, 255, 255, 0.3); -fx-padding: 5px; -fx-border-radius: 8px;"   
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
                  characterScrollPane,
                  new Label("Available Foods - Drag to match characters") {
                        styleClass += "section-header"
                        style = "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.7); -fx-padding: 6px 12px; -fx-background-radius: 4px;"
                  },
                  foodScrollPane
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

      // METHOD: Create Character Box
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

      // METHOD: Setup food items
      private def setupFoods(): Unit = {
            foodHBox.children.clear()
            
            // DEBUG
            println(s"Setting up ${gameEngine.availableFoods.size} food items in scrollable container")
            
            gameEngine.availableFoods.foreach { food =>
                  println(s"Creating food box for ${food.name}")
                  val foodBox = createFoodBox(food)
                  foodHBox.children.add(foodBox)
            }
            
            val totalFoods = gameEngine.availableFoods.size
            val minContentWidth = totalFoods * 135 
            foodHBox.minWidth = math.max(minContentWidth, 600)
            
            println(s"Food container setup complete with ${totalFoods} foods")
      }

      // METHOD: Create Food Item Box
      private def createFoodBox(food: FoodItem): Pane = {
            // CONTAINER FOR FOOD ITEM BOXES
            val box = new VBox {
                  spacing = 3 
                  alignment = Pos.Center
                  styleClass += "food-box"
                  prefWidth = 110
                  maxWidth = 110
                  minWidth = 110
                  prefHeight = 110 
                  maxHeight = 110
                  
                  visible <== !food.isMatchedProperty
                  managed <== !food.isMatchedProperty
            }
            
            // FOOD ITEM IMAGE
            val imageView = new ImageView {
                  image = food.image
                  fitWidth = 60 // Consistent size
                  fitHeight = 60
                  preserveRatio = true
                  style = "-fx-background-color: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            }
            
            // FOOD ITEM NAME
            val nameLabel = new Label(food.name) {
                  styleClass += "food-name"
                  style = "-fx-font-size: 11px; -fx-font-weight: bold;"
                  wrapText = true
                  maxWidth = 100
                  textAlignment = TextAlignment.Center
            }
            
            // FOOD ITEM CATEGORY
            val categoryLabel = new Label(s"Type: ${food.primaryCategory}") {
                  styleClass += "food-category-label"
                  style = "-fx-font-style: italic; -fx-text-fill: #1976D2; -fx-background-color: rgba(255,255,255,0.9); -fx-padding: 1px 3px; -fx-background-radius: 3px; -fx-font-size: 9px;"
                  wrapText = true
                  maxWidth = 100
                  textAlignment = TextAlignment.Center
            }
            
            // COMPONENTS OF FOOD ITEM BOX
            box.children = List(
                  imageView,
                  nameLabel,
                  categoryLabel
            )

            // SERVICES - Drag and Drop
            dragDropService.registerDragSource(
                  box, 
                  food,
                  food => gameEngine.selectFood(food),
                  food => gameEngine.deselectFood()
            )

            // EFFECTS
            box.onMouseEntered = _ => {
                  FxEffects.applyPulseEffect(box)
            }
            box.onMouseExited = _ => {
                  FxEffects.removeEffect(box)
            }
            box.onMouseClicked = _ => {
                  if (gameEngine.selectedFood == null) {
                  gameEngine.selectFood(food)
                  FxEffects.applyGlowEffect(box, Color.Gold)
                  } else if (gameEngine.selectedFood == food) {
                  gameEngine.deselectFood()
                  FxEffects.removeEffect(box)
                  }
            }
            box
      }

      // METHOD: To set up the game controls + pre-game screen 
      private def setupGameControls(): Unit = {
            // START GAME OVERLAY (Shown before game as an initial cover screen)
            val startGameOverlay = new StackPane() {
                  alignment = Pos.Center
                  styleClass += "game-overlay"
                  
                  prefWidth = 1024
                  prefHeight = 768
       
                  root.width.onChange { (_, _, newWidth) =>
                  prefWidth = newWidth.doubleValue()
                  }
                  root.height.onChange { (_, _, newHeight) =>
                  prefHeight = newHeight.doubleValue()
                  }
            }

            // START GAME OVERLAY - Rectangle 
            val overlayRect = new Rectangle {
                  width = 1024
                  height = 768
                  
                  startGameOverlay.width.onChange { (_, _, newWidth) =>
                  width = newWidth.doubleValue()
                  }
                  startGameOverlay.height.onChange { (_, _, newHeight) =>
                  height = newHeight.doubleValue()
                  }
                  
                  fill = Color.rgb(0, 0, 0, 0.7)
            }

            // START GAME OVERLAY - Content
            val overlayContent = new VBox {
                  spacing = 20
                  alignment = Pos.Center
                  maxWidth = 600
                  padding = Insets(30)
                  
                  children = Seq(
                        new Label(s"${gameEngine.levelConfig.name}") {
                              styleClass += "overlay-title"
                              wrapText = true
                        },
                        new Label(gameEngine.levelConfig.description) {
                              styleClass += "overlay-text"
                              wrapText = true
                        },
                        new Label("Match food items to characters based on the nutritional prompts of each character. Use scroll bars if needed to see all characters and foods.") {
                              styleClass += "overlay-instructions"
                              wrapText = true
                        },
                        new Button("Start Game") {
                              styleClass += "start-button"
                              minWidth = 200
                              minHeight = 50
                              onAction = _ => {
                                    startGameOverlay.visible = false
                                    gameEngine.start()
                              }
                        }
                  )
            }

            // ADD CONTENT TO OVERLAY
            startGameOverlay.children = Seq(overlayRect, overlayContent)
            val gameArea = createGameArea()

            val combinedLayout = new StackPane {
                  children = Seq(
                        gameArea,
                        startGameOverlay
                  )
            }

            root.center = combinedLayout
      }

      // METHOD: Handle game events via the game engine
      private def handleGameEvent(event: GameEvent): Unit = {
            event match {
                  case GameEvent.GameCompleted(finalScore) =>
                        println("Game completed, navigating to results screen")
                        val resultObj = sceneManager.goTo(Routes.Result)
                  
                        // DEBUG
                        println(s"Result object: $resultObj")
                        if (resultObj != null) {
                              println(s"Result object class: ${resultObj.getClass.getName}")
                        }
                  
                        if (resultObj != null) {
                              try {
                                    val controller = resultObj.asInstanceOf[ResultController]
                                    println(s"Successfully got ResultController: $controller")
                                    controller.setResults(gameEngine.mode, finalScore)
                              } catch {
                                    case e: ClassCastException =>
                                          println(s"Error casting controller: ${e.getMessage}")
                                          e.printStackTrace()
                                    case e: Exception =>
                                          println(s"Unexpected error with controller: ${e.getMessage}")
                                          e.printStackTrace()
                              }
                  } else {
                        println("Warning: No controller returned from sceneManager.goTo(Routes.Result)")
                  }
                  
                  case GameEvent.CorrectMatch(food, character) =>
                        println(s"Correct match: ${food.name} -> ${character.name}")
                  
                  
                  val characterBoxes = characterHBox.children.filtered(node => 
                        node.getId == character.name
                  )
                  
                  if (characterBoxes.size() > 0) {
                        FxEffects.applySpinningGreenEffect(characterBoxes.get(0))
                  
                        val pauseTransition = new PauseTransition(Duration(1.5))
                        pauseTransition.onFinished = _ => {
                              applyMatchedStyling(character)
                        }
                        pauseTransition.play()
                  }
                  
                  case GameEvent.IncorrectMatch(food, character) =>
                        val characterBoxes = characterHBox.children.filtered(node => 
                              node.getId == character.name
                        )
                        
                        if (characterBoxes.size() > 0) {
                              FxEffects.applyShakeEffect(characterBoxes.get(0))
                        }
                  
                  case _ => // INTENTIONALLY LEFT BLANK
            }
      }

}