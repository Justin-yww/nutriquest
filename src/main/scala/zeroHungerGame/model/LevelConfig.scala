package zeroHungerGame.model

import scala.util.Random

// PURPOSE: Configuration settings for different game levels
class LevelConfig private(
      val mode: String,
      val timeLimit: Int,
      val characters: List[Character],
      val availableFoods: List[FoodItem],
      val educationText: List[String]
) {
      // Validation the level configuration 
      require(timeLimit > 0, "Time limit must be positive")
      require(characters.nonEmpty, "Level must have at least one character")
      require(availableFoods.nonEmpty, "Level must have at least one food item")

      // PURPOSE: Get the educational section text for display 
      def formattedEducationText: String = {
            educationText.mkString("\n\n")
      }

      // PURPOSE: To display the descriptive name for each respective level
      def name: String = mode match {
            case "village"  => "MODE 1: Village Mode 🛖"
            case "urban"    => "MODE 2: Urban Mode 🏙️"
            case _          => s"${mode.capitalize} Mode"
      }

      // PURPOSE: Provide a description for each respective level
      def description: String = mode match {
            case "village" => 
                  "Help village residents maintain proper nutrition by matching local food sources to their dietary needs."
            case "urban" => 
                  "Help urban residents maintain proper nutrition by matching available food sources to their dietary needs in a busy city environment."
            case _ => 
                  s"Match foods to characters based on their nutritional needs."
      }

      override def toString: String = {
            s"LevelConfig($mode, timeLimit=$timeLimit, characters=${characters.size}, foods=${availableFoods.size})"
  }
}

object LevelConfig {
      // Create VILLAGE Mode 
      def createVillageLevel(): LevelConfig ={ 
            // CHARACTER CREATION 
            // CHARACTER: [1] Farmer
            val mrChong = new VillageCharacter(
                  "Mr. Chong", 
                  "Farmer", 
                  "Works long days cultivating rice in the paddy fields and managing crop harvests",
                  NutritionProfile.forFarmer(),
                  "images/characters/farmer.png",
                  8
            )

            // CHARACTER: [2] Grandmother
            val ahMah = new VillageCharacter(
                  "Ah Mah", 
                  "Grandmother", 
                  "Village elder who takes care of family and needs proper nutrition for bone health",
                  NutritionProfile.forElderly(),
                  "images/characters/grandmother.png",
                  2
            )

            // CHARACTER: [3] Fisherman
            val mrArjun = new VillageCharacter(
                  "Mr. Arjun", 
                  "Fisherman", 
                  "Spends long hours fishing in rivers and needs sustained energy for water-based work",
                  NutritionProfile.forFisherman(),
                  "images/characters/fisherman.png",
                  6
            )

            // CHARACTER: [4] Butcher
            val mrAli = new VillageCharacter(
                  "Mr. Ali", 
                  "Butcher", 
                  "Works in the meat industry and requires balanced nutrition with sufficient vegetable intake",
                  NutritionProfile.forButcher(),
                  "images/characters/butcher.png",
                  4
            )

            // CHARACTER: [5] Young Child
            val ahmad = new VillageCharacter(
                  "Ahmad", 
                  "Young Child", 
                  "Growing child who needs balanced protein nutrients to ensure proper development",
                  NutritionProfile.forChild(),
                  "images/characters/child.png",
                  1
            )

            // FOOD ITEM CREATION - VILLAGE MODE
            val foods = List(
                  FoodItem.create("rice"),       
                  FoodItem.create("milk"),       
                  FoodItem.create("fish"),       
                  FoodItem.create("vegetables"), 
                  FoodItem.create("beans")       
            )

            val randomizedFoods = Random.shuffle(foods)

            // EDUCATIONAL CONTENT - VILLAGE MODE 
            val education = List(
                  "Page 1",
                  "Page 2",
                  "Page 3",
                  "Page 4",
                  "Page 5"
            )

            new LevelConfig(
                  "village",
                  120, 
                  List(mrChong, ahMah, mrArjun, mrAli, ahmad),
                  randomizedFoods,
                  education
            )
      }

      // Create URBAN Mode
      def createUrbanLevel(): LevelConfig = {
            // CHARACTER CREATION
            // CHARACTER: [1] Office Worker
            val mrLim = new UrbanCharacter(
                  "Mr. Lim", 
                  "Office Worker", 
                  "Spends long hours at a desk and needs vitamin-rich foods to maintain alertness and reduce fatigue",
                  NutritionProfile.forOfficeWorker(),
                  "images/characters/office_worker.png",
                  8
            )

            // CHARACTER: [2] Professional Athlete
            val msSofea = new UrbanCharacter(
                  "Ms. Sofea", 
                  "Professional Athlete", 
                  "High-performance athlete requiring quality protein for muscle repair and high calorie demands",
                  NutritionProfile.forAthlete(),
                  "images/characters/athlete.png",
                  6
            )

            // CHARACTER: [3] Construction Worker
            val mrRavi = new UrbanCharacter(
                  "Mr. Ravi", 
                  "Construction Worker", 
                  "Performs physically demanding construction work and needs steady carbohydrates for sustained energy",
                  NutritionProfile.forConstructionWorker(),
                  "images/characters/construction_worker.png",
                  10
            )

            // CHARACTER: [4] Pregnant Woman
            val msNurul = new UrbanCharacter(
                  "Ms. Nurul", 
                  "Pregnant Woman", 
                  "Expecting mother who needs protein and folate to support baby's development and maternal health",
                  NutritionProfile.forPregnantWoman(),
                  "images/characters/pregnant_woman.png",
                  0
            )
            
            // CHARACTER: [5] College Student
            val ethan = new UrbanCharacter(
                  "Ethan", 
                  "College Student", 
                  "University student requiring omega-3 fatty acids to support brain health and memory for studies",
                  NutritionProfile.forStudent(),
                  "images/characters/student.png",
                  4
            )

            // CHARACTER: [6] Ride-Hailing Driver
            val mrDanial = new UrbanCharacter(
                  "Mr. Danial", 
                  "Ride-Hailing Driver", 
                  "Spends long hours driving and needs quick energy and potassium to prevent muscle cramps",
                  NutritionProfile.forDriver(),
                  "images/characters/driver.png",
                  12
            )

            // CHARACTER: [7] Software Developer
            val msAdrianna = new UrbanCharacter(
                  "Ms. Adrianna", 
                  "Software Developer", 
                  "Works long hours coding and needs healthy fats for sustained energy and concentration",
                  NutritionProfile.forSoftwareDeveloper(),
                  "images/characters/developer.png",
                  8
            )

            // CHARACTER: [8] Nurse
            val mrChen = new UrbanCharacter(
                  "Mr. Chen", 
                  "Nurse", 
                  "Healthcare worker on long shifts who needs calcium for bone strength and convenient nutrition",
                  NutritionProfile.forNurse(),
                  "images/characters/nurse.png",
                  12
            )

            // CHARACTER: [9] Retail Cashier
            val msPriyah = new UrbanCharacter(
                  "Ms. Priyah", 
                  "Retail Cashier", 
                  "Stands for long shifts with limited breaks and needs fiber for sustained energy and fullness",
                  NutritionProfile.forCashier(),
                  "images/characters/cashier.png",
                  8
            )

            // FOOD ITEM CREATION - URBAN MODE
            val foods = List(
                  FoodItem.create("orange"),          
                  FoodItem.create("chicken"),         
                  FoodItem.create("rice"),            
                  FoodItem.create("beans"),           
                  FoodItem.create("fish"),            
                  FoodItem.create("banana"),          
                  FoodItem.create("mixed nuts"),      
                  FoodItem.create("yogurt"),             
                  FoodItem.create("whole-grain bread")   
            )

            val randomizedFoods = Random.shuffle(foods)

            // EDUCATIONAL CONTENT 
            val education = List(
                  "Page 1",
                  "Page 2",
                  "Page 3",
                  "Page 4",
                  "Page 5"
            )

            new LevelConfig(
                  "urban",
                  60, 
                  List(mrLim, msSofea, mrRavi, msNurul, ethan, mrDanial, msAdrianna, mrChen, msPriyah),
                  randomizedFoods,
                  education
            )
      }

      // PURPOSE: Choose the game mode 
      def forMode(mode: String): LevelConfig = { 
            mode match { 
                  case "village" => createVillageLevel()
                  case "urban" => createUrbanLevel()
                  case _ => throw new IllegalArgumentException(s"Unknown mode: $mode")
            }
      }
} 