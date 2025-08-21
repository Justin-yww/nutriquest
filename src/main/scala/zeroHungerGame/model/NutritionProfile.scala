package zeroHungerGame.model

import scalafx.beans.property.{IntegerProperty, ObjectProperty}
import scala.collection.mutable.Map

class NutritionProfile(
      initialRequiredTags: Set[String], 
      initialDailyCalories: Int, 
      val primaryNeed: String
){
      // Properties for data binding 
      private val _requiredTags = ObjectProperty[Set[String]](initialRequiredTags)
      private val _dailyCalories = IntegerProperty(initialDailyCalories)
      private val _fulfilledTags = ObjectProperty[Map[String, Boolean]](
            initialRequiredTags.map(tag => tag -> false).to(Map)
      )

      // Getters
      def requiredTags: Set[String] = _requiredTags.value
      def dailyCalories: Int = _dailyCalories.value
      def fulfilledTags: Map[String, Boolean] = _fulfilledTags.value

      // Accessors
      def requiredTagsProperty = _requiredTags
      def dailyCaloriesProperty = _dailyCalories
      def fulfilledTagsProperty = _fulfilledTags

      // PURPOSE: To check if food items matches the nutrition profile
      def matchesFood(food: FoodItem): Boolean = { 
            val needMatches = food.primaryCategory.toLowerCase.replace("-", " ") == 
                     primaryNeed.toLowerCase.replace("-", " ")
            
            val needMatchesAlternative = (primaryNeed.toLowerCase, food.primaryCategory.toLowerCase) match {
                        case ("carbs", "carbohydrates") | ("carbohydrates", "carbs") => true
                        case ("vitamin", "vitamins") | ("vitamins", "vitamin") => true
                        case ("healthy fats", "fats") | ("fats", "healthy fats") => true
                        case ("omega-3", "omega 3") | ("omega 3", "omega-3") => true
                        case ("protein", "proteins") | ("proteins", "protein") => true
                        case ("fiber", "fibre") | ("fibre", "fiber") => true
                        case ("calcium", "cal") | ("cal", "calcium") => true
                        case ("potassium", "k") | ("k", "potassium") => true
                        case _ => false
            }

            needMatches || needMatchesAlternative
      }

      // PURPOSE: Update the with the fulfilled tags if the food item matches
      def consumeFood(food: FoodItem): Set[String] = {
            val newlyFulfilled = requiredTags.filter(tag => food.hasTag(tag) && !fulfilledTags(tag))
            newlyFulfilled.foreach(tag => fulfilledTags(tag) = true)
            newlyFulfilled
      }

      // PURPOSE: Ensure that all tags are fulfilled
      def isComplete: Boolean = {
            fulfilledTags.values.forall(identity)
      }

      // PURPOSE: Reset the tags for a new game
      def reset(): Unit = {
            requiredTags.foreach(tag => fulfilledTags(tag) = false)
      }

      // PURPOSE: Get the percentage of fulfillment tags
      def completionPercentage: Int = {
            if (requiredTags.isEmpty) {
                  100
            } else {
                  val fulfilledCount = fulfilledTags.count(_._2)
                  (fulfilledCount.toDouble / requiredTags.size.toDouble * 100).toInt
            }
      }

      override def toString: String = { 
            s"NutritionProfile(dailyCalories=$dailyCalories, primaryNeed=$primaryNeed, requiredTags=[${requiredTags.mkString(", ")}])"
      }
}

object NutritionProfile { 
      // VILLAGE CHARACTERS 
      // [1] Farmer
      def forFarmer(): NutritionProfile = {
            new NutritionProfile(
                  Set("carbs", "energy", "grain"),
                  2400,
                  "Carbs"
            )
      }      

      // [2] Grandmother
      def forElderly(): NutritionProfile = {
            new NutritionProfile(
                  Set("protein", "calcium", "dairy"),
                  1800,
                  "Protein"
            )
      }

      // [3] Fisherman 
      def forFisherman(): NutritionProfile = {
            new NutritionProfile(
                  Set("protein", "omega-3", "energy"),
                  2600,
                  "Protein"
            )
      }
      
      // [4] Butcher
      def forButcher(): NutritionProfile = {
            new NutritionProfile(
                  Set("fiber", "vegetable", "vitamin"),
                  2200,
                  "Fiber"
            )
      }

      // [5] Young Child 
      def forChild(): NutritionProfile = {
            new NutritionProfile(
                  Set("protein", "fiber", "calcium"),
                  1600,
                  "Protein"
            )
      }      

      // URBAN CHARACTERS
      // [A] Office Worker
      def forOfficeWorker(): NutritionProfile = {
            new NutritionProfile(
                  Set("vitamin", "fruit", "energy"),
                  2000,
                  "Vitamin"
            )
      }
      
      // [B] Pro Athlete
      def forAthlete(): NutritionProfile = {
            new NutritionProfile(
                  Set("protein", "meat", "energy"),
                  3000,
                  "Protein"
            )
      }

      // [C] Construction Worker
      def forConstructionWorker(): NutritionProfile = {
            new NutritionProfile(
                  Set("carbs", "grain", "energy"),
                  2800,
                  "Carbs"
            )
      }

      // [D] Pregnant Woman 
      def forPregnantWoman(): NutritionProfile = {
            new NutritionProfile(
                  Set("protein", "fiber", "folate"),
                  2400,
                  "Protein"
            )
      }

      // [E] College Students
      def forStudent(): NutritionProfile = {
            new NutritionProfile(
                  Set("omega-3", "protein", "brain-health"),
                  2200,
                  "Omega-3"
            )
      }

      // [F] Ride-Hailling Driver
      def forDriver(): NutritionProfile = {
            new NutritionProfile(
                  Set("potassium", "fruit", "quick-energy"),
                  2200,
                  "Potassium"
            )
      }

      // [G] Software Developer
      def forSoftwareDeveloper(): NutritionProfile = {
            new NutritionProfile(
                  Set("healthy fats", "protein", "focus"),
                  2000,
                  "Healthy Fats"
            )
      }

      // [H] Nurse
      def forNurse(): NutritionProfile = {
            new NutritionProfile(
                  Set("calcium", "dairy", "protein"),
                  2200,
                  "Calcium"
            )
      }

      // [I] Retail Cashier
      def forCashier(): NutritionProfile = {
            new NutritionProfile(
                  Set("fiber", "grain", "carbs"),
                  1800,
                  "Fiber"
            )
      }

      // GENERIC PROFILE
      def forAdult(): NutritionProfile = {
            new NutritionProfile(
                  Set("protein", "carb", "vitamin", "fiber"),
                  2200,
                  "Balanced"
            )
      }

      // CUSTOM PROFILE - Nutrition Profile
      def custom(tags: Set[String], calories: Int, primaryNeed: String): NutritionProfile = {
            new NutritionProfile(tags, calories, primaryNeed)
      }
}