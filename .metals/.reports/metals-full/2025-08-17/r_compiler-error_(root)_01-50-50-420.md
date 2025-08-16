file://<WORKSPACE>/src/main/scala/zeroHungerGame/ui/GameView.scala
### java.lang.StringIndexOutOfBoundsException: Range [4253, 4259) out of bounds for length 4256

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 4253
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/ui/GameView.scala
text:
```scala
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

      ..@@

}
```



#### Error stacktrace:

```
java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55)
	java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210)
	java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98)
	java.base/jdk.internal.util.Preconditions.outOfBoundsCheckFromToIndex(Preconditions.java:112)
	java.base/jdk.internal.util.Preconditions.checkFromToIndex(Preconditions.java:349)
	java.base/java.lang.String.checkBoundsBeginEnd(String.java:4914)
	java.base/java.lang.String.substring(String.java:2876)
	dotty.tools.pc.completions.CompletionProvider.mkItem$1(CompletionProvider.scala:248)
	dotty.tools.pc.completions.CompletionProvider.completionItems(CompletionProvider.scala:347)
	dotty.tools.pc.completions.CompletionProvider.$anonfun$1(CompletionProvider.scala:149)
	scala.collection.immutable.List.map(List.scala:247)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:141)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: Range [4253, 4259) out of bounds for length 4256