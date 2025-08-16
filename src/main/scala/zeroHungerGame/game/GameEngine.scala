package zeroHungerGame.game

import scalafx.beans.property.{BooleanProperty, IntegerProperty, ObjectProperty, StringProperty}
import scala.collection.mutable

import zeroHungerGame.model.{Character, FoodItem, LevelConfig}

class GameEngine(initialMode: String) {
      // Goal of Game Engine: Manages game state and logic (works with 'model' files)
      // STATE PROPERTIES
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

      // GAMEENGINE Services 
      private val timerService    = new TimerService(this)
      private val dragDropService  = new DragDropService()

      // SCORING MECHANISM - base 
      private val baseMatchScore = 10 
      private val timeRemainingMultiplier = 2

      // SCORING MECHANISM - Speed multipliers 
      private val speedMultiplierMax = 5.0
      private val speedMultiplierMin = 1.0
      private val speedScoreRange = speedMultiplierMax - speedMultiplierMin

      // GETTERs
      def running: Boolean = _running.value
      def score: Int = _score.value
      def timeRemaining: Int = _timeRemaining.value

      def levelConfigOption: Option[LevelConfig] = _levelConfig.value
      def levelConfig: LevelConfig = _levelConfig.value.getOrElse(throw new IllegalStateException("LevelConfig not set"))

      def characters: List[Character] = _characters.value

      def availableFoods: List[FoodItem] = _availableFoods.value
      def selectedFoodOption: Option[FoodItem] = _selectedFood.value

      def gameStatus: String = _gameStatus.value

      def mode: String = _levelConfig.value.map(_.mode).getOrElse(initialMode)

      // ACCESSORS 
      def runningProperty        = _running
      def scoreProperty          = _score
      def timeRemainingProperty  = _timeRemaining
      def levelConfigProperty    = _levelConfig        
      def charactersProperty     = _characters
      def availableFoodsProperty = _availableFoods
      def selectedFood: FoodItem = _selectedFood.value.orNull
      def selectedFoodProperty   = _selectedFood       
      def gameStatusProperty     = _gameStatus

      // SETTING OF GAME MODE AND INITIALISE LEVEL 
      def setMode(newMode: String): Unit = {
            val config = LevelConfig.forMode(newMode)

            _levelConfig.value    = Some(config)
            _timeRemaining.value  = config.timeLimit
            _characters.value     = config.characters
            _availableFoods.value = config.availableFoods

            // RESET - for new game
            characters.foreach(_.nutritionProfile.reset())
            availableFoods.foreach(_.isMatched = false)

            _score.value       = 0
            _running.value     = false
            _gameStatus.value  = "ready"
            _selectedFood.value = None 
            matchedCharacters.clear()

            notifyListeners(GameEvent.ModeChanged(newMode))
      }

      // START GAME
      def start(): Unit = {
            if (gameStatus != "running") {
              _running.value    = true
              _gameStatus.value = "running"
              timerService.start()
              notifyListeners(GameEvent.GameStarted)
            }
      }

      // PAUSE GAME
      def pause(): Unit = {
            if (gameStatus == "running") {
                  _running.value    = false
                  _gameStatus.value = "paused"
                  timerService.pause()
                  notifyListeners(GameEvent.GamePaused)
            }
      }

      // RESUME GAME
      def resume(): Unit = {
            if (gameStatus == "paused") {
                  _running.value    = true
                  _gameStatus.value = "running"
                  timerService.resume()
                  notifyListeners(GameEvent.GameResumed)
            }
      }

      // STOP GAME + SCORE CALCULATION
      def stop(): Unit = {
            if (gameStatus == "running" || gameStatus == "paused") {
                  _running.value    = false
                  _gameStatus.value = "completed"
                  timerService.stop()

                  val timeBonus = timeRemaining * timeRemainingMultiplier
                  _score.value  = score + timeBonus

                  notifyListeners(GameEvent.GameCompleted(_score.value))
            }
      }

      // RESET GAME - change back to the initial state
      def reset(): Unit = setMode(mode)

      // FOOD ITEM MATCHING - Selection
      def selectFood(food: FoodItem): Unit = {
            if (running && !food.isMatched) {
                  _selectedFood.value = Some(food)                 
                  notifyListeners(GameEvent.FoodSelected(food))
            }
      }

      // FOOD ITEM MATCHING - Deselection
      def deselectFood(): Unit = {
            _selectedFood.value.foreach { f => 
                  _selectedFood.value = None
                  notifyListeners(GameEvent.FoodDeselected(f))
            }
      }

      // FOOD ITEM MATCHING - Item-Character
      def matchFoodToCharacter(character: Character): Boolean = {
            if (!running) return false
            
            // FOR COMPLETED CHARACTERS
            if (matchedCharacters.contains(character)) return false

            _selectedFood.value match {                             
                  case Some(food) =>
                        if (character.matchesFood(food)) {
                              food.isMatched = true
                              matchedCharacters.add(character)
                              
                              // SCORE LOGIC 
                              // Score Calculation 
                              val timeRatio = timeRemaining.toDouble / levelConfig.timeLimit.toDouble
                              
                              // Speed multiplier for quicker reflexes from user
                              val speedMultiplier = speedMultiplierMin + (speedScoreRange * timeRatio)
                              
                              // Apply the multiplier to the base score
                              val matchScore = (baseMatchScore * speedMultiplier).toInt
                              
                              // Update total score
                              _score.value = _score.value + matchScore
                              
                              _selectedFood.value = None
                              notifyListeners(GameEvent.CorrectMatch(food, character))
                              if (matchedCharacters.size == characters.size) {
                                    stop()
                              }
                        
                              true
                        } else {
                        notifyListeners(GameEvent.IncorrectMatch(food, character))
                        false
                        }
                  case None =>
                        false
            }
      }

      // TIMERSERVICE - Update time
      def updateTime(secondsRemaining: Int): Unit = {
            _timeRemaining.value = secondsRemaining

            if (secondsRemaining <= 0 && running) {
                  stop()
            }
      }

      // MANAGEMENT OF THE EVENT LISTENER
      def addEventListener(listener: GameEvent => Unit): Unit =
            gameEventListeners.add(listener)

      def removeEventListener(listener: GameEvent => Unit): Unit =
            gameEventListeners.remove(listener)

      private def notifyListeners(event: GameEvent): Unit =
            gameEventListeners.foreach(_(event))
}

sealed trait GameEvent
object GameEvent {
  case object GameStarted extends GameEvent
  case object GamePaused extends GameEvent
  case object GameResumed extends GameEvent
  case class GameCompleted(finalScore: Int) extends GameEvent
  case class ModeChanged(mode: String) extends GameEvent
  case class FoodSelected(food: FoodItem) extends GameEvent
  case class FoodDeselected(food: FoodItem) extends GameEvent
  case class CorrectMatch(food: FoodItem, character: Character) extends GameEvent
  case class IncorrectMatch(food: FoodItem, character: Character) extends GameEvent
}