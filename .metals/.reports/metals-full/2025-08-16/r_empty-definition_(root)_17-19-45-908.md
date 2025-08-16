error id: file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala:`<none>`.
file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -fulfilledTags.
	 -fulfilledTags#
	 -fulfilledTags().
	 -scala/Predef.fulfilledTags.
	 -scala/Predef.fulfilledTags#
	 -scala/Predef.fulfilledTags().
offset: 3042
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala
text:
```scala
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
            if (requiredTags.isEmpty) 100
            else (fulfilledTags.count(identity).toDouble / requiredTags.size * 100).toInt
            else (fulfilledTag@@s.count(identity).toDouble / requiredTags.size * 100).toInt
      }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.