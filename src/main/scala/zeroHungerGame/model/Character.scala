package zeroHungerGame.model

import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.image.Image

import zeroHungerGame.util.ResourceLoader

trait CharacterBase {
      def name: String 
      def role: String
      def description: String
      def nutritionProfile: NutritionProfile
      def imagePath: String
      def primaryNutritionNeed: String
      def nutritionPrompt: String
}

class Character(
      initialName: String, 
      initialRole: String,
      initialDescription: String,
      initialNutritionProfile: NutritionProfile,
      initialImagePath: String,
) extends CharacterBase {
      // For UI Binding 
      private val _name              = StringProperty(initialName)
      private val _role              = StringProperty(initialRole)
      private val _description       = StringProperty(initialDescription)
      private val _nutritionProfile  = ObjectProperty[NutritionProfile](initialNutritionProfile)
      private val _imagePath         = StringProperty(initialImagePath)
      private val _nutritionPrompt   = StringProperty(computeNutritionPrompt(initialName, initialRole))

      private val _imageCache = ObjectProperty[Image](

            new Image(ResourceLoader.loadImagePath("placeholder.png"))
      )

      // Getters 
      def name: String = _name.value
      def role: String = _role.value
      def description: String = _description.value
      def nutritionProfile: NutritionProfile = _nutritionProfile.value
      def imagePath: String = _imagePath.value
      def nutritionPrompt: String = _nutritionPrompt.value
      
      // Setters
      def name_=(v: String): Unit                 = {
      _name.value = v
      _nutritionPrompt.value = computeNutritionPrompt(v, role)
      }
      def role_=(v: String): Unit                 = { 
      _role.value = v 
      _nutritionPrompt.value = computeNutritionPrompt(name, v)
      }
      def description_=(v: String): Unit          = _description.value = v
      def nutritionProfile_=(v: NutritionProfile): Unit = _nutritionProfile.value = v

      // Accessors
      def nameProperty              = _name
      def roleProperty              = _role
      def descriptionProperty       = _description
      def nutritionProfileProperty  = _nutritionProfile
      def nutritionPromptProperty   = _nutritionPrompt

      def image: Image = { 
            val relPath = imagePath.stripPrefix("images/")
            val url     = ResourceLoader.loadImagePath(relPath)
            val safeUrl = 
                  if (url == null || url.isEmpty || url.startsWith("data:"))
                     ResourceLoader.loadImagePath("placeholder.png")
                  else url
            val img = new Image(safeUrl)
            _imageCache.value = img
            _imageCache.value
      }

      def primaryNutritionNeed: String = nutritionProfile.primaryNeed 

      // PURPOSE: Helper method to determine nutrition prompt
      // NOTE: Nutrition prompt needs to be shorter to ensure it is visible 
      /*  
      TOOL : "============================================================="
      Use this to measure if nutrition prompts are too long, it should not exceed the tool above
      */ 
      private def computeNutritionPrompt(charName: String, charRole: String): String = {
            (charName.toLowerCase, charRole.toLowerCase) match {
                  // CHARACTERS - Village Mode
                  case ("mr. chong", "farmer") => 
                        "Needs carbs for energy during long hours in the fields"
                  case ("ah mah", "grandmother") => 
                        "Needs milk protein to strengthen aging bones"
                  case ("mr. arjun", "fisherman") => 
                        "Needs protein to maintain energy for river fishing"
                  case ("mr. ali", "butcher") => 
                        "Needs fiber from vegetables to balance meat-heavy diet"
                  case ("ahmad", "young child") => 
                        "Needs protein for healthy growth and development"

                  // CHARACTERS - Urban Mode
                  case ("mr. lim", "office worker") => 
                        "Needs vitamins to stay alert during desk work"
                  case ("ms. sofea", "professional athlete") => 
                        "Needs protein for muscle repair after training"
                  case ("mr. ravi", "construction worker") => 
                        "Needs carbs for energy during physical labor"
                  case ("ms. nurul", "pregnant woman") => 
                        "Needs protein for baby's development and health"
                  case ("ethan", "college student") => 
                        "Needs omega-3 for brain function and focus"
                  case ("mr. danial", "ride-hailing driver") => 
                        "Needs potassium to prevent cramps while driving"
                  case ("ms. adrianna", "software developer") => 
                        "Needs healthy fats for concentration during coding"
                  case ("mr. chen", "nurse") => 
                        "Needs calcium for bone strength during long shifts"
                  case ("ms. priyah", "retail cashier") => 
                        "Needs fiber for energy during standing shifts"
            }
      }

      // PURPOSE: Check if the food item matches the character 
      def matchesFood(food: FoodItem): Boolean = {
            (name.toLowerCase, food.name.toLowerCase) match {
                  // Village Mode
                  case ("mr. chong", "rice") => true
                  case ("ah mah", "milk") => true
                  case ("mr. arjun", "fish") => true
                  case ("mr. ali", "vegetables") => true
                  case ("ahmad", "beans") => true

                  // Urban Mode 
                  case ("mr. lim", "orange") => true
                  case ("ms. sofea", "chicken") => true
                  case ("mr. ravi", "rice") => true
                  case ("ms. nurul", "beans") => true
                  case ("ethan", "salmon") => true
                  case ("mr. danial", "banana") => true
                  case ("ms. adrianna", "mixed nuts") => true
                  case ("mr. chen", "yogurt") => true
                  case ("ms. priyah", "whole-grain bread") => true

                  case _ => nutritionProfile.matchesFood(food)
            }
      }

      override def toString: String = s"Character($name, $role, primaryNeed=$primaryNutritionNeed)"
  
}

// CLASS: Village Characater
class VillageCharacter(
  name: String,
  role: String,
  description: String,
  nutritionProfile: NutritionProfile,
  imagePath: String,
  val farmingSkill: Int
) extends Character(name, role, description, nutritionProfile, imagePath) {
  override def toString: String = s"VillageCharacter($name, $role, primaryNeed=$primaryNutritionNeed, farmingSkill=$farmingSkill)"
}

// CLASS: Urban Characater
class UrbanCharacter(
  name: String,
  role: String,
  description: String,
  nutritionProfile: NutritionProfile,
  imagePath: String,
  val workHours: Int
) extends Character(name, role, description, nutritionProfile, imagePath) {
  override def toString: String = s"UrbanCharacter($name, $role, primaryNeed=$primaryNutritionNeed, workHours=$workHours)"
}