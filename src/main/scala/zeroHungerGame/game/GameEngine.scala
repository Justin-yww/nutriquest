package zeroHungerGame.game

import scalafx.beans.property.{BooleanProperty, IntegerProperty, ObjectProperty, StringProperty}
import scala.collection.mutable

import zeroHungerGame.model.{Character, FoodItem, LevelConfig}

class GameEngine {
      // Goal of Game Engine: Manages game state and logic (works with 'model' files)
      // STATE
      private val _running       = BooleanProperty(false)
      private val _score         = IntegerProperty(0)
      private val _timeRemaining = IntegerProperty(0)
      private val _levelConfig   = ObjectProperty[Option[LevelConfig]](None)
      private val _characters    = ObjectProperty[List[Character]](List.empty[Character])
      private val _availableFoods= ObjectProperty[List[FoodItem]](List.empty[FoodItem])
      private val _selectedFood  = ObjectProperty[Option[FoodItem]](None)
      private val _gameStatus    = StringProperty("ready")

      // Used when there's a match
      private val matchedCharacters = mutable.Set[Character]()

      // Event listeners for game events
      private val gameEventListeners = mutable.Set[GameEvent => Unit]()

      // Initialise engine 
      setMode(initialMode)

      // Services TO BE ADDED later on 
      // NOTE: Work on services first 
}