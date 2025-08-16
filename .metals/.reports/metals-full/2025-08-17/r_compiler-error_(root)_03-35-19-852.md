file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/ResultsController.scala
### java.lang.StringIndexOutOfBoundsException: Range [7356, 7362) out of bounds for length 7359

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 7356
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/ResultsController.scala
text:
```scala
package zeroHungerGame.controller

import scalafx.Includes._
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, ProgressBar}
import javafx.scene.layout.{Pane, VBox}
import java.net.URL
import java.util.ResourceBundle

import zeroHungerGame.core.{Routes, SceneManager}
import zeroHungerGame.util.FxEffects
import zeroHungerGame.game.GameEngine

// PURPOSE: Helps control the results screen
class ResultController(sceneManager: SceneManager) extends Initializable {
      // FXML fields
      @FXML private var titleLabel: Label = _
      @FXML private var scoreLabel: Label = _
      @FXML private var feedbackLabel: Label = _
      @FXML private var progressBar: ProgressBar = _
      @FXML private var tryAgainButton: Button = _
      @FXML private var tryOtherModeButton: Button = _
      @FXML private var mainMenuButton: Button = _

      // STATE
      private var gameMode: String = ""
      private var score: Int = 0

      override def initialize(location: URL, resources: ResourceBundle): Unit = {
            println("ResultController initializing...")

            // PURPOSE: Rerun the game in the same mode
            if (tryAgainButton != null) {
                  tryAgainButton.setOnAction(_ => {
                        println("Try again button clicked")
                        if (gameMode == "village") {
                              sceneManager.clearAndRestart(Routes.GameVillage)
                        } else {
                              sceneManager.clearAndRestart(Routes.GameUrban)
                        }
                  })
            } else {
                  println("WARNING: tryAgainButton is null!")
            }

            // PURPOSE: Switch to another mode
            if (tryOtherModeButton != null) {
                  tryOtherModeButton.setOnAction(_ => {
                        println("Try other mode button clicked")
                        if (gameMode == "village") {
                              sceneManager.startFreshGame(Routes.UrbanEducation(1))
                        } else {
                              sceneManager.startFreshGame(Routes.VillageEducation(1))
                        }
                  })
            } else {
                  println("WARNING: tryOtherModeButton is null!")
            }

            // PURPOSE: Go back to the main menu
            if (mainMenuButton != null) {
                  mainMenuButton.setOnAction(_ => {
                        println("Main menu button clicked")
                        sceneManager.clearGameScreensAndGoToMenu()
                  })
            } else {
                  println("WARNING: mainMenuButton is null!")
            }

            // PURPOSE: Setup button effects
            try {
                  setupButtonEffects()
            } catch {
                  case e: Exception =>
                  println(s"Error setting up button effects: ${e.getMessage}")
                  e.printStackTrace()
            }

            def setResults(mode:String, fianlScore:Int): Unit = {
                  println(s"Setting results: mode=$mode, score=$finalScore")
    
                  gameMode = mode
                  score = finalScore
                  
                  updateUI()
                  
                  if (tryOtherModeButton != null) {
                        val otherMode = if (mode == "village") "Urban" else "Village"
                        tryOtherModeButton.setText(s"Try $otherMode Mode")
                  }
            } 
      }

      private def updateUI(): Unit = {
            if (titleLabel != null) {
                  titleLabel.setText(s"${gameMode.capitalize} Challenge Results")
            }

            // PURPOSE: Update score display
            if (scoreLabel != null) {
                  scoreLabel.setText(s"Your Score: $score")
            }

            // SCORING MECHANISM (range + message)
            // AI DECLARATION: AI was used to generate the scoring messages and help iron out the scoring mechanisms (how points were calculated)
            val feedback = (gameMode, score) match {
                  // VILLAGE MODE
                  // AI GENERATED: Theoretical max for village: ~490 points (5 characters × up to 50 each + time bonus)
                  case ("village", s) if s >= 400 => "Exceptional! Perfect speed and strategy - you're a nutrition master!"
                  case ("village", s) if s >= 300 => "Outstanding! Excellent knowledge of village nutrition concepts!"
                  case ("village", s) if s >= 200 => "Great job! You have a solid understanding of village nutrition!"
                  case ("village", s) if s >= 150 => "Good work! You're getting the hang of village nutrition matching!"
                  case ("village", s) if s >= 100 => "Nice effort! Keep practicing to improve your village nutrition knowledge!"
                  case ("village", s) if s >= 50 => "Getting started! Try to match foods more quickly for better scores!"
                  case ("village", _) => "Keep trying! Focus on matching the right foods to each character's needs!"
                  
                  // URBAN MODE
                  // AI GENERATED: Theoretical max for urban: ~570 points (9 characters × up to 50 each + time bonus)
                  case ("urban", s) if s >= 500 => "Phenomenal! You've mastered urban nutrition with perfect speed!"
                  case ("urban", s) if s >= 400 => "Excellent! Outstanding knowledge of urban nutrition concepts!"
                  case ("urban", s) if s >= 300 => "Great performance! You understand urban nutrition very well!"
                  case ("urban", s) if s >= 200 => "Good job! Solid grasp of urban nutrition matching!"
                  case ("urban", s) if s >= 150 => "Nice work! You're developing good urban nutrition knowledge!"
                  case ("urban", s) if s >= 100 => "Decent effort! Practice more to improve your urban nutrition skills!"
                  case ("urban", s) if s >= 50 => "Getting there! Try to work faster for higher scores!"
                  case ("urban", _) => "Keep practicing! Focus on understanding each character's specific needs!"
                  
                  // EDGE CASES
                  case (_, s) if s >= 400 => "Exceptional performance! You really understand nutrition concepts!"
                  case (_, s) if s >= 300 => "Excellent work! Outstanding knowledge of nutrition matching!"
                  case (_, s) if s >= 200 => "Great job! Good understanding of nutrition principles!"
                  case (_, s) if s >= 100 => "Nice work! Keep practicing to improve your skills!"
                  case (_, _) => "Good effort! Focus on matching foods to characters' specific nutritional needs!"
            }
            
            if (feedbackLabel != null) {
                  feedbackLabel.setText(feedback)
            }
            
            try {
                  FxEffects.applyFadeInEffect(scoreLabel)
                  if (feedbackLabel != null) {
                  FxEffects.applyFadeInEffect(feedbackLabel, delay = 0.5)
                  }
            } catch {
                  case e: Exception =>
                  println(s"Error applying animations: ${e.getMessage}")
                  e.printStackTrace()
            }
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

java.lang.StringIndexOutOfBoundsException: Range [7356, 7362) out of bounds for length 7359