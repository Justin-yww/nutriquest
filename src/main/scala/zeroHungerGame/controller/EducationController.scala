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
      case class EducationContent(text: String, imagePath: Option[String] = None)

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
                              RURAL NUTRITION BASICS 🌾
                              
                              Welcome to the basics about Kampung Nutrition

                              🛖 In villages, food comes straight from farms and nature.

                              🍅 Villagers often grow their own food and might have less options compared to that of cities. 

                              🥬 Fresh Vegetables, fruits, grains, and even fish and meat make up most village meals. 

                              💪 Good nutrition helps villagers stay healthy, grow stronger, and have sufficient energy for everyday activities like farming or fishing. 

                              QUESTION: What is your favourite traditional kampung snack or food?""",
                              Some("education/village_page1.png")

                        ), 
                        // VILLAGE MODE - PAGE 2
                        EducationContent(
                              """
                              CHALLENGES IN VILLAGE NUTRITION ⚠️

                              Villagers sometimes face these nutrition challenges:

                              🌧️ Recent climate change has led to unpredictable rain and flooding that can affect the growing of crops. 

                              🍚 An unbalanced diet and lack of variety has resulted in many villagers eating too much rice or other carbs (like noodles).

                              🥬Limited access to vegetables when the season of vegetables are not in season. 

                              💧Clean water might not always be accessible and lead to food poisoning due to the water quality. 

                              Villagers need to be educated to learn more about potential food options that they could grow to help with their nutritional needs.""",
                              Some("education/village_page2.png")

                        ), 
                        // VILLAGE MODE - PAGE 3
                        EducationContent(
                              """
                              VILLAGE FOOD GROUPS 🛖

                              Here are some of the major food groups found in villages:

                              🌾 CARBOHYDRATE: Rice, bread, and potatoes helps give you the energy you need to work on everyday activities. 

                              🥚 PROTEINS: Beans, fish, eggs and meat all help build strong muscles and promotes sturdy growth. 

                              🥕 VEGETABLES: Vegetables help by providing you with fiber and prevent you from getting sick. 

                              🍉 FRUITS: Important for your health as it provides vitamins.

                              🐄 DAIRY: Milk helps your bones grow strong with calcium. 

                              A balanced diet consists of foods from all the following groups.""",
                              Some("education/village_page3.png")

                        ), 
                        // VILLAGE MODE - PAGE 4
                        EducationContent(
                              """
                              VILLAGE NUTRITION TIPS 💡 

                              Smart ways to eat healthy in the village: 

                              🫘 Mix grains (like rice) with proteins (like beans) to make increase the nutritional value of meals. 

                              🌱 Try growing new types of vegetables and fruits to improve variety of the crops and to avoid shortages during different seasons. 

                              👶 Growing children require more protein and calcium to ensure healthy bones and steady growth. 

                              FUN FACT: Many of the healthiest families in the world live in villages where they eat fresh food they grow themselves.""",
                              Some("education/village_page4.png")

                        ), 
                        // VILLAGE MODE - PAGE 5
                        EducationContent(
                              """
                              GET READY FOR THE VILLAGE MODE GAME 🛖

                              There will be 5 characters and 5 food items to match from.

                              1. Read the descriptive prompt given below each character's card to understand their nutritional requirement

                              2. Drag and drop the food items onto the character cards that match their needs.

                              3. Complete all matches before the time runs out to win the game!

                              Press start game to proceed with the game.""",
                              Some("education/village_page5.png")

                        )
                  )

                  case "urban" => Array (
                        // URBAN MODE - PAGE 1
                        EducationContent(
                              """
                              URBAN NUTRITION BASICS 🥡

                              Welcome to the basics about Bandar Nutrition

                              🏙️ In cities, food options come from all across the world which leads to more choices.

                              🛒 Urban people often buy food from stores and markets instead of growing it like in the village. 

                              🍟 The busier lifestyle within the urban setting has lead to fast food options which are unhealthy. 

                              💪 Good nutrition helps people think clearly and stay healthy. 

                              QUESTION: What's your favorite healthy food that you can find in the city?""",
                              Some("education/urban_page1.png")

                        ),
                        // URBAN MODE - PAGE 2
                        EducationContent(
                              """
                              CHALLENGES IN URBAN NUTRITION ⚠️

                              Urban families sometimes face these nutrition challenges:

                              🍔 Too much fast food options leads to unhealthy consumption habits.

                              ⏰ Busy schedules can lead to many people skipping meals or substituting entire meals with unhealthy snacks. 

                              🥗 Healthier food options may cost more and meal preparation requires additional time. 

                              🏃 Insufficient physical activities combined with a higher calorie and sugary diet can have severe health impacts. 

                              Making healthy choices requires a lot of extra effort in the city.""",
                              Some("education/urban_page2.png")

                        ),
                        // URBAN MODE - PAGE 3
                        EducationContent(
                              """
                              URBAN FOOD GROUPS 🛖

                              Here are some of the major food groups found in villages:

                              🥐 CARBOHYDRATES: Bread, pasta, multigrain rice, and pastries give the energy and should be consumed in moderation. 

                              🍖 PROTEINS: Chicken, fish and beans all help build strong muscles and repairs body tissue. 

                              🥕 VEGETABLES: Vegetables help by providing you with fiber and minerals to prevent you from getting sick. 

                              🍊 FRUITS: Important for your health as it provides vitamins and fiber, it also can replace unhealthy snacks. 

                              🧀 DAIRY: Yogurt and cheese provide calcium alternatives for your bones to stay strong.

                              🥑 HEALTHY FATS: Nuts and avocados help provide the good fats for your brain.

                              Everyone in the city should have a healthy mixture of all these groups to live a healthy life.""",
                              Some("education/urban_page3.png")

                        ),
                        // URBAN MODE - PAGE 4
                        EducationContent(
                              """
                              URBAN NUTRITION TIPS 💡 

                              Smart ways to eat healthy in the city: 

                              🗓️ Plan and preapre your meals on the weekends to help save cost and avoid unhealthy fast food options during the busy week. 

                              🫐 Substitute sugary and processed snacks with healthier options like fruits to help with healthy eating habits in between meals. 

                              🥤 Avoid sugary drinks and try to substitute with fresh juices or water. 

                              FUN FACT: People who eat homemade meals tend to be much healthier than those who eat out.""",
                              Some("education/urban_page4.png")

                        ),
                        // URBAN MODE - PAGE 5
                        EducationContent(
                              """
                              GET READY FOR THE URBAN MODE GAME 🏙️

                              There will be 9 characters and 9 food items to match from.

                              1. Read the descriptive prompt given below each character's card to understand their nutritional requirement

                              2. Drag and drop the food items onto the character cards that match their needs.

                              3. Complete all matches before the time runs out to win the game!

                              Press start game to proceed with the game. 
                              """,
                              Some("education/urban_page5.png")

                        )
                  )

                  case _ => Array(
                        EducationContent("Page 1 Education Content - Not Found"),
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
            
            if (contentContainer != null) { 
                  contentContainer.getChildren.clear()

                  // Text Section
                  if (pageNumber <= educationalContents.length) {
                        val textArea = new TextArea{
                              setText(educationalContents(pageNumber - 1))
                              setWrapText(true)
                              setEditable(false)
                              setPrefHeight(220)
                              getStyleClass.add("edu-content")
                              setStyle("-fx-font-size: 14px; -fx-font-family: 'SF Pro Text', sans-serif;")
                        }
                        contentContainer.getChildren.add(textArea)
                  }
                  // Image Section
                  if (pageNumber - 1 < educationImagePaths.length) { 
                        educationImagePaths(pageNumber - 1).foreach { imagePath => 
                              try {
                                    val imageUrl = ResourceLoader.loadImagePath(imagePath)
                                    val image = new javafx.scene.image.Image(imageUrl)
                                    val imageView = new ImageView {
                                          setImage(image)
                                          setFitWidth(400)
                                          setPreserveRatio(true)
                                          setSmooth(true)
                                          getStyleClass.add("edu-image")
                                          setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2); -fx-background-color: white; -fx-padding: 5px; -fx-background-radius: 5px;")
                                    }

                                    val imageContainer = new HBox {
                                          setAlignment(javafx.geometry.Pos.CENTER)
                                          getChildren.add(imageView)
                                          setStyle("-fx-padding: 10px;")
                                    }

                                    contentContainer.getChildren.add(imageContainer)
                              } catch {
                                    case e: Exception => 
                                          println(s"Error loading image $imagePath: ${e.getMessage}")
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