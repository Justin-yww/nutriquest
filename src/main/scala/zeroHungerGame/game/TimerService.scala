package zeroHungerGame.game

import scalafx.animation.{Animation, KeyFrame, Timeline}
import scalafx.util.Duration
import javafx.event.{ActionEvent => JActionEvent}
import javafx.event.{EventHandler => JEventHandler}

class TimerService(gameEngine: GameEngine) {
      private var timeRemaining: Int = 0

      // COUNTDOWN TIMER for the Game 
      private val timeline: Timeline = new Timeline {
            cycleCount = Animation.Indefinite
            keyFrames = Seq(
                  KeyFrame(
                        Duration(1000),
                        onFinished = new JEventHandler[JActionEvent] {
                              override def handle(event: JActionEvent): Unit = {
                                    tick()
                              }
                        }
                  )
            )
      }

      // START TIMER
      def start(): Unit = {
            // Initialise with the time limit based on mode (VILLAGE OR URBAN)
            /*
            VILLAGE: 120 seconds
            URBAN: 60 seconds
            */
            timeRemaining = gameEngine.levelConfig.timeLimit
            gameEngine.updateTime(timeRemaining)
            timeline.playFromStart()
      }

      // PAUSE TIMER
      def pause(): Unit = {
            timeline.pause()
      }

      // RESUME TIMER
      def resume(): Unit = {
            timeline.play()
      }

      // STOP TIMER 
      def stop(): Unit = {
            timeline.stop()
      }

      // PROCESS TIMER TICK (one second)
      private def tick(): Unit = {
            timeRemaining -= 1

            // PREVENT NEGATIVE TIME
            if (timeRemaining < 0) {
            timeRemaining = 0
            }

            gameEngine.updateTime(timeRemaining)

            // Stop the time
            // NOTE: Game Engine methods is the one handling the 'game over' logic
            if (timeRemaining <= 0) {
                  stop()
            }
      }
      // PRODUCE FORMATTED TIME STRING
      def formattedTime: String = {
            val minutes = timeRemaining / 60
            val seconds = timeRemaining % 60
            f"$minutes%02d:$seconds%02d"
      }
} 