package zeroHungerGame.util

import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.Includes._
import scala.util.{Try, Success, Failure}
import javafx.util.Duration
import java.io.File

object AudioService {
  
      private var backgroundMusicPlayer: Option[MediaPlayer] = None
      
      // ADJUST VOLUME HERE: 
      private var volumeLevel: Double = 0.3
      private var muted: Boolean = false
      
      def init(): Unit = {
            println("Initializing AudioService...")
            loadBackgroundMusic()
      }
      
      def loadBackgroundMusic(resourcePath: String = "sounds/background.mp3"): Unit = {
      Try {
            val url = getClass.getResource(s"/zeroHungerGame/$resourcePath")
            val mediaUrl = if (url == null) {
                  val file = new File(s"src/main/resources/zeroHungerGame/$resourcePath")
                  if (file.exists()) {
                        file.toURI.toString
                  } else {
                        println(s"Warning: Background music file not found at $resourcePath")
                        return
                  }
            } else {
                  url.toString
            }
            
            println(s"Loading background music from: $mediaUrl")
            
            val media = new Media(mediaUrl)
            val player = new MediaPlayer(media)
            
            player.volume = volumeLevel
            player.cycleCount = MediaPlayer.Indefinite 
            
            player.onEndOfMedia = () => {
                  player.seek(Duration.ZERO)
                  player.play()
            }
            
            // Store player reference
            backgroundMusicPlayer = Some(player)
            
            // Start playing
            player.play()
            
            println("Background music started successfully")
      } match {
            case Success(_) => println("Background music loaded successfully")
            case Failure(e) => 
                  println(s"Error loading background music: ${e.getMessage}")
                  e.printStackTrace()
      }
      }

      // PURPOSE: To load and play background music
      def playBackgroundMusic(): Unit = {
            backgroundMusicPlayer.foreach(_.play())
      }

      // PURPOSE: To pause the background music if it's playing.
      def pauseBackgroundMusic(): Unit = {
            backgroundMusicPlayer.foreach(_.pause())
      }

      // PURPOSE: To stop the background music.
      def stopBackgroundMusic(): Unit = {
            backgroundMusicPlayer.foreach(_.stop())
      }

      // PURPOSE: To set the volume for all audio.
      def setVolume(volume: Double): Unit = {
            volumeLevel = math.max(0.0, math.min(1.0, volume)) 
            
            if (!muted) {
                  backgroundMusicPlayer.foreach(_.volume = volumeLevel)
            }
      }

      // PURPOSE: To toggle mute state for all audio.
      def toggleMute(): Boolean = {
            muted = !muted
            
            backgroundMusicPlayer.foreach(_.volume = if (muted) 0.0 else volumeLevel)
            
            muted
      }

      // PURPOSE: To set mute state for all audio.
      def setMuted(muteState: Boolean): Unit = {
            muted = muteState
            backgroundMusicPlayer.foreach(_.volume = if (muted) 0.0 else volumeLevel)
      }

      // PURPOSE: To clean up resources when the application is shutting down.
      def shutdown(): Unit = {
            println("Shutting down AudioService...")
            backgroundMusicPlayer.foreach { player =>
                  player.stop()
                  player.dispose()
            }
            backgroundMusicPlayer = None
      }

      // PURPOSE: To play a sound effect 
      def playSoundEffect(resourcePath: String): Unit = {
            Try {
                  val url = getClass.getResource(s"/zeroHungerGame/$resourcePath")
                  
                  val mediaUrl = if (url == null) {
                        val file = new File(s"src/main/resources/zeroHungerGame/$resourcePath")
                        if (file.exists()) {
                              file.toURI.toString
                        } else {
                              println(s"Warning: Sound effect file not found at $resourcePath")
                              return
                        }
                  } else {
                        url.toString
                  }
                  
                  val media = new Media(mediaUrl)
                  val player = new MediaPlayer(media)
                  
                  player.volume = if (muted) 0.0 else volumeLevel
                  player.cycleCount = 1 // Play once
                  
                  player.onEndOfMedia = () => {
                        player.dispose()
                  }
                  
                  player.play()
            } match {
                  case Success(_) => 
                  case Failure(e) => 
                        println(s"Error playing sound effect: ${e.getMessage}")
            }
      }
}