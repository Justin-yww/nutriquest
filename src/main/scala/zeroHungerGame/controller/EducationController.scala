package zeroHungerGame.controller

import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, ScrollPane, TextArea}
import javafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import javafx.scene.image.ImageView
import java.net.URL
import java.util.ResourceBundle

import zeroHungerGame.core.{Routes, SceneManager}
import zeroHungerGame.model.LevelConfig
import zeroHungerGame.util.{ResourceLoader}

class EducationController(sceneManager: SceneManager) extends Initializable {
      // FXML fields
      @FXML private var titleLabel: Label = _
      @FXML private var pageNumberLabel: Label = _ 
      @FXML private var contentArea: TextArea = _
      @FXML private var characterContainer: HBox = _
      @FXML private var foodContainer: HBox = _
      @FXML private var previousButton: Button = _ 
      @FXML private var nextButton: Button = _     
      @FXML private var skipButton: Button = _     
      @FXML private var backToMenuButton: Button = _

      // State 
      private var mode: String = "village"
      private var pageNumber: Int = 1
      private var totalPages: Int = 5
      private var educationalContents: Array[String] = Array.empty

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

      // METHOD: TO load the education content for both modes (VILLAGE and URBAN)
      private def loadEducationalContent(): Unit = {
            println(s"Loading educational content for $mode mode")

            val baseContent = mode match {
                  case "village" => "Rural Nutrition Education"
                  case "urban" => "Urban Nutrition Education"
                  case _ => "Nutrition Education"
            }

            // PAGES OF THE EDUCATION CONTENT
            educationalContents = Array(
                  s"$baseContent - Page 1\n\nIntroduction to nutrition and its importance in ${mode} context.\n\nProper nutrition is essential for health and wellbeing, regardless of where you live.",
                  
                  s"$baseContent - Page 2\n\nCommon nutritional challenges in ${mode} environments.\n\nDifferent environments present unique challenges to maintaining a balanced diet.",
                  
                  s"$baseContent - Page 3\n\nKey food groups and their nutritional benefits.\n\nUnderstanding the different food groups helps in planning balanced meals.",
                  
                  s"$baseContent - Page 4\n\nTips for maintaining a balanced diet in ${mode} settings.\n\nPractical advice for addressing common nutritional challenges.",
                  
                  s"$baseContent - Page 5\n\nSummary and preparation for the nutrition matching game.\n\nNow it's time to test your knowledge in a fun and interactive way!"
            )

            // Establish the total number of pages 
            totalPages = educationalContents.length

            println(s"Loaded $totalPages pages of educational content")
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
            
            if (contentArea != null && pageNumber <= educationalContents.length) {
                  contentArea.setText(educationalContents(pageNumber - 1))
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