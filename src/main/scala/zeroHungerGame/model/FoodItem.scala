package zeroHungerGame.model

import scalafx.beans.property.{BooleanProperty, IntegerProperty, ObjectProperty, StringProperty}
import scalafx.scene.image.Image
import scala.collection.mutable

import zeroHungerGame.util.ResourceLoader

class FoodItem(
      initialName: String,
      initialCalories: Int,
      initialTags: Set[String],
      initialImagePath: String
){
      private val _name       = StringProperty(initialName)
      private val _calories   = IntegerProperty(initialCalories)
      private val _tags       = ObjectProperty[Set[String]](initialTags)
      private val _imagePath  = StringProperty(initialImagePath)
      private val _imageCache = ObjectProperty[Image](
      new Image(ResourceLoader.loadImagePath("placeholder.png"))
      )
      private val _isMatched  = BooleanProperty(false)

      def name: String            = _name.value
      def calories: Int           = _calories.value
      def tags: Set[String]       = _tags.value
      def imagePath: String       = _imagePath.value
      def isMatched: Boolean      = _isMatched.value

      def name_=(v: String): Unit       = _name.value = v
      def calories_=(v: Int): Unit      = _calories.value = v
      def tags_=(v: Set[String]): Unit  = _tags.value = v
      def isMatched_=(v: Boolean): Unit = _isMatched.value = v

      def nameProperty      = _name
      def caloriesProperty  = _calories
      def tagsProperty      = _tags
      def isMatchedProperty = _isMatched

      // Get image from cache
      def image: Image = {
            val relPath = imagePath.stripPrefix("images/")  
            val url     = ResourceLoader.loadImagePath(relPath)
            val safe    =
                  if (url == null || url.isEmpty || url.startsWith("data:"))
                  ResourceLoader.loadImagePath("placeholder.png")
                  else url
            val img = new Image(safe)
            _imageCache.value = img
            _imageCache.value
      }

      def hasTag(tag: String): Boolean = tags.contains(tag)
      def satisfies(requiredTags: Set[String]): Boolean = requiredTags.subsetOf(tags)

      // PURPOSE: Get the primary nutritional category (BASED ON PROMPT)
      def primaryCategory: String = {
            // Extract primary category with food name
            name.toLowerCase match {
                  // Village Mode
                  case "rice" => "Carbs"
                  case "milk" => "Protein"
                  case "fish" => "Protein" // Used in both modes (focus is on omega-3 for URBAN MODE)
                  case "vegetables" => "Fiber"
                  case "beans" => "Protein"

                  // Urban Mode
                  case "orange" => "Vitamin"
                  case "chicken" => "Protein"
                  case "banana" => "Potassium"
                  case "mixed nuts" => "Healthy Fats"
                  case "yogurt" => "Calcium"
                  case "whole-grain bread" => "Fiber"
                  
                  case _ => 
                        val priorities = List(
                              "carbs", "protein", "fiber", "vitamin", "calcium", 
                              "omega-3", "potassium", "healthy fats", "dairy", 
                              "fruit", "vegetable", "grain", "meat"
                        )

                        priorities.find(priority => tags.exists(_.toLowerCase.replace("-", " ") == priority.toLowerCase.replace("-", " ")))
                              .getOrElse(tags.headOption.getOrElse("Mixed"))
                              .split(" ").map(_.capitalize).mkString(" ")
            }
      }

      override def toString: String =
      s"FoodItem($name, calories=$calories, tags=[${tags.mkString(", ")}])"

}

object FoodItem { 
      private val foodDatabase = mutable.Map[String, FoodItem]()

      def create(foodType: String): FoodItem = {
            foodType.toLowerCase.replace(" ", "").replace("-", "") match {
                  // Village Mode Items 
                  case "rice"       => new FoodItem("Rice",       200, Set("carbs", "grain", "energy"),         "images/foods/rice.png")
                  case "milk"       => new FoodItem("Milk",       120, Set("protein", "calcium", "dairy"),      "images/foods/milk.png")
                  case "fish"       => new FoodItem("Fish",       180, Set("protein", "omega-3"),               "images/foods/fish.png")
                  case "vegetables" => new FoodItem("Vegetables",  40, Set("fiber", "vitamin", "vegetable"),    "images/foods/vegetables.png")
                  case "beans"      => new FoodItem("Beans",      150, Set("protein", "fiber", "folate"),       "images/foods/beans.png")

                  // Urban Mode Items
                  case "orange"           => new FoodItem("Orange",      80, Set("vitamin", "fruit", "energy"),       "images/foods/orange.png")
                  case "chicken"          => new FoodItem("Chicken",    250, Set("protein", "meat"),                  "images/foods/chicken.png")
                  case "banana"           => new FoodItem("Banana",     105, Set("potassium", "fruit", "quick-energy"), "images/foods/banana.png")
                  case "mixednuts"        => new FoodItem("Mixed Nuts", 180, Set("healthy fats", "protein", "focus"), "images/foods/mixed_nuts.png")
                  case "yogurt"           => new FoodItem("Yogurt",     100, Set("calcium", "protein", "dairy"),      "images/foods/yogurt.png")
                  case "wholegrainbread"  => new FoodItem("Whole-Grain Bread", 120, Set("fiber", "carbs", "grain"), "images/foods/whole_grain_bread.png")
                  
                  // Handling Variations 
                  case foodname if foodname.contains("mixed") && foodname.contains("nuts") => new FoodItem("Mixed Nuts", 180, Set("healthy fats", "protein", "focus"), "images/foods/mixed_nuts.png")
                  case foodname if foodname.contains("whole") && foodname.contains("grain") => new FoodItem("Whole-Grain Bread", 120, Set("fiber", "carbs", "grain"), "images/foods/whole_grain_bread.png")

                  case _            => new FoodItem(foodType.split(" ").map(_.capitalize).mkString(" "), 100, Set("unknown"), "images/placeholder.png")
            }
      }

      def get(foodType: String): FoodItem =
            foodDatabase.getOrElseUpdate(foodType, create(foodType))   
}