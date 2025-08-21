file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/EducationController.scala
### java.lang.AssertionError: assertion failed: position error, parent span does not contain child span
parent      = new ImageView(_root_.scala.Predef.???) # -1,
parent span = <9085..9099>,
child       = _root_.scala.Predef.??? # -1,
child span  = <9099..9131>

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 6617
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/controller/EducationController.scala
text:
```scala
package zeroHungerGame.controller

import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, ScrollPane, TextArea}
import javafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import javafx.scene.image.ImageView
import java.net.URL
import java.util.ResourceBundle

import zeroHungerGame.core.{Routes, SceneManager}
import zeroHungerGame.model.LevelConfig
import zeroHungerGame.util.{FxEffects, ResourceLoader}

class EducationController(sceneManager: SceneManager) extends Initializable {
      // FXML fields
      @FXML private var titleLabel: Label = _
      @FXML private var pageNumberLabel: Label = _ 
      @FXML private var contentContainer: VBox = _
      @FXML private var characterContainer: HBox = _
      @FXML private var foodContainer: HBox = _
      @FXML private var previousButton: Button = _ 
      @FXML private var nextButton: Button = _     
      @FXML private var skipButton: Button = _     
      @FXML private var backToMenuButton: Button = _

      // Implement a case class to help store educational content (May include images)
      case class EducationalContent(text: String, imagePath: Option[String] = None)

      // State 
      private var mode: String = "village"
      private var pageNumber: Int = 1
      private var totalPages: Int = 5
      private var educationalContents: Array[String] = Array.empty
      private var educationImagePaths: Array[Option[String]] = Array.empty

      override def initialize(location: URL, resources: ResourceBundle): Unit = {
            println("EducationController initializing...")

            // PURPOSE: Set up button actions
            if (previousButton != null) {
                  previousButton.setOnAction(_ => {
                        goToPreviousPage()
                  })
            } else {
                  println("WARNING: previousButton is null!")
            }
            
            if (nextButton != null) {
                  nextButton.setOnAction(_ => {
                        goToNextPage()
                  })
            } else {
                  println("WARNING: nextButton is null!")
            }
            
            if (skipButton != null) {
                  skipButton.setOnAction(_ => {
                        skipToGame()
                  })
            } else {
                  println("WARNING: skipButton is null!")
            }
            
            if (backToMenuButton != null) {
                  backToMenuButton.setOnAction(_ => {
                        sceneManager.goTo(Routes.Menu)
                  })
            } else {
                  println("WARNING: backToMenuButton is null!")
            }
      }

      //  METHOD: Set the education mode and page
      def setModeAndPage(newMode: String, page: Int): Unit = {
           println(s"Setting mode to $newMode and page to $page")
           mode = newMode
           pageNumber = page

           loadEducationalContent()

           updatePage()
      }

      // METHOD: To load the education content for both modes (VILLAGE and URBAN)
      private def loadEducationalContent(): Unit = {
            println(s"Loading educational content for $mode mode")

            // Managing the content displayed based on the mode: VILLAGE and URBAN 
            val educationPages = mode match {
                  case "village" => Array (
                        // VILLAGE MODE - PAGE 1
                        EducationContent(
                              """
                              PAGE 1 TO BE UPTADED LATER
                              """,
                              Some("education/village_page1.png")

                        ), 
                        // VILLAGE MODE - PAGE 2
                        EducationContent(
                              """
                              PAGE 2 TO BE UPTADED LATER
                              """,
                              Some("education/village_page2.png")

                        ), 
                        // VILLAGE MODE - PAGE 3
                        EducationContent(
                              """
                              PAGE 3 TO BE UPTADED LATER
                              """,
                              Some("education/village_page3.png")

                        ), 
                        // VILLAGE MODE - PAGE 4
                        EducationContent(
                              """
                              PAGE 4 TO BE UPTADED LATER
                              """,
                              Some("education/village_page4.png")

                        ), 
                        // VILLAGE MODE - PAGE 5
                        EducationContent(
                              """
                              PAGE 5 TO BE UPTADED LATER
                              """,
                              Some("education/village_page5.png")

                        )
                  )

                  case "urban" => Array (
                        // URBAN MODE - PAGE 1
                        EducationContent(
                              """
                              PAGE 1 TO BE UPTADED LATER
                              """,
                              Some("education/urban_page1.png")

                        ),
                        // URBAN MODE - PAGE 2
                        EducationContent(
                              """
                              PAGE 2 TO BE UPTADED LATER
                              """,
                              Some("education/urban_page2.png")

                        ),
                        // URBAN MODE - PAGE 3
                        EducationContent(
                              """
                              PAGE 3 TO BE UPTADED LATER
                              """,
                              Some("education/urban_page3.png")

                        ),
                        // URBAN MODE - PAGE 4
                        EducationContent(
                              """
                              PAGE 4 TO BE UPTADED LATER
                              """,
                              Some("education/urban_page4.png")

                        ),
                        // URBAN MODE - PAGE 5
                        EducationContent(
                              """
                              PAGE 5 TO BE UPTADED LATER
                              """,
                              Some("education/urban_page5.png")

                        )
                  )

                  case _ => Array(
                        EducationContent@@("Page 1 Education Content - Not Found"),
                        EducationContent("Page 2 Education Content - Not Found"),
                        EducationContent("Page 3 Education Content - Not Found"),
                        EducationContent("Page 4 Education Content - Not Found"),
                        EducationContent("Page 5 Education Content - Not Found")
                  )

            }

            educationalContents = educationPages.map(_.text)
            educationImagePaths = educationPages.map(_.imagePath)

            // Establish the total number of pages 
            totalPages = educationalContents.length

            println(s"Loaded $totalPages pages of educational contents based on mode: $mode")
      }

      // METHOD: Update UI to display the current education page
      private def updatePage(): Unit = {
            println(s"Updating page to $pageNumber of $totalPages")
            
            if (pageNumber < 1) pageNumber = 1
            if (pageNumber > totalPages) pageNumber = totalPages
            
            if (pageNumberLabel != null) {
                  pageNumberLabel.setText(s"Page $pageNumber of $totalPages")
            }
            
            if (titleLabel != null) {
                  titleLabel.setText(s"${mode.capitalize} Nutrition Education")
            }
            
            if (contentContainer != null){ 
                  contentContainer.getChildren.clear()

                  // Text Section
                  if (pageNumber <= educationalContents.length) {
                        setText(educationalContents(pageNumber - 1))
                        setWrapText(true)
                        setEditable(false)
                        setPrefHeight(220)
                        getStyleClass.add("edu-content")
                        setStyle("-fx-font-size: 14px; -fx-font-family: 'Comic Sans MS', cursive, sans-serif;")setText(education)
                  }
                  contentContainer.getChildren.add(textArea)

                  // Image Section
                  if (pageNumber - 1 < educationImagePaths.length) { 
                        educationImagePaths(pageNumber - 1).foreach { imagePath => 
                              try {
                                    val imageUrl = ResourceLoader.loadImagePath(imagePath)
                                    val image = new java.fx.scene.image.Image(imageUrl)
                                    val imageView = new ImageView(

                              }
                        
                        }
                  }
            }

            }
            
            if (previousButton != null) {
                  previousButton.setDisable(pageNumber <= 1)
            }
            
            if (nextButton != null) {
                  if (pageNumber >= totalPages) {
                  nextButton.setText("Start Game")
                  } else {
                  nextButton.setText("Next")
                  }
            }
      }

      // METHOD: To navigate back to the previous page
      private def goToPreviousPage(): Unit = {
            println("Going to previous page")
            
            if (pageNumber > 1) {
                  pageNumber -= 1
                  updatePage()
            }
      }

      // METHOD: To navigate to the next page OR start the game
      private def goToNextPage(): Unit = {
            println("Going to next page")
            
            if (pageNumber < totalPages) {
                  // FIXED: Just directly update the page number instead of recreating the controller
                  pageNumber += 1
                  updatePage()
            } else {
                  // Start the game if on the last page
                  skipToGame()
            }
      }

      // METHOD: Offer the option to skip the educational content straight to the game
      private def skipToGame(): Unit = {
            println("Skipping to game")
            
            mode match {
                  case "village" => sceneManager.goTo(Routes.GameVillage)
                  case "urban" => sceneManager.goTo(Routes.GameUrban)
                  case _ => // INTENTIONALLY LEFT BLANK
            }
      }
     
}
```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:175)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.parsing.Parser.parse$$anonfun$1(ParserPhase.scala:39)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:467)
	dotty.tools.dotc.parsing.Parser.parse(ParserPhase.scala:40)
	dotty.tools.dotc.parsing.Parser.$anonfun$2(ParserPhase.scala:52)
	scala.collection.Iterator$$anon$6.hasNext(Iterator.scala:479)
	scala.collection.Iterator$$anon$9.hasNext(Iterator.scala:583)
	scala.collection.immutable.List.prependedAll(List.scala:152)
	scala.collection.immutable.List$.from(List.scala:685)
	scala.collection.immutable.List$.from(List.scala:682)
	scala.collection.IterableOps$WithFilter.map(Iterable.scala:900)
	dotty.tools.dotc.parsing.Parser.runOn(ParserPhase.scala:51)
	dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:315)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1324)
	dotty.tools.dotc.Run.runPhases$1(Run.scala:308)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:348)
	dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:357)
	dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:69)
	dotty.tools.dotc.Run.compileUnits(Run.scala:357)
	dotty.tools.dotc.Run.compileSources(Run.scala:261)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:161)
	dotty.tools.pc.CachingDriver.run(CachingDriver.scala:45)
	dotty.tools.pc.AutoImportsProvider.autoImports(AutoImportsProvider.scala:34)
	dotty.tools.pc.ScalaPresentationCompiler.autoImports$$anonfun$1(ScalaPresentationCompiler.scala:279)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: position error, parent span does not contain child span
parent      = new ImageView(_root_.scala.Predef.???) # -1,
parent span = <9085..9099>,
child       = _root_.scala.Predef.??? # -1,
child span  = <9099..9131>