package zeroHungerGame.util

import scalafx.animation.{FadeTransition, PauseTransition, RotateTransition, ScaleTransition, SequentialTransition, Timeline}
import scalafx.Includes._
import scalafx.scene.Node
import scalafx.scene.effect.{DropShadow, Glow}
import scalafx.scene.paint.Color
import scalafx.util.Duration
import javafx.animation.{KeyFrame => JKeyFrame, KeyValue => JKeyValue, Interpolator => JInterpolator}

object FxEffects { 
      // APPLY GLOW EFFECT
      def applyGlowEffect(node: Node, glowColor: Color = Color.Gold, level: Double = 0.5): Unit = {
            val dropShadow = new DropShadow()
            dropShadow.color = glowColor
            dropShadow.radius = 15
            dropShadow.spread = 0.25
            
            val glow = new Glow(level)
            glow.input = dropShadow
            
            node.effect = glow
      }

      // STORAGE OF ALL ANIMATIONS
      private val nodeAnimations = new scala.collection.mutable.WeakHashMap[Node, Timeline]()

      // ANIMATION - Pulse Effect
      def applyPulseEffect(node: Node, duration: Double = 0.8): Unit = {
            val originalScaleX = node.scaleX()
            val originalScaleY = node.scaleY()
            
            val timeline = new Timeline()
            timeline.cycleCount = Timeline.Indefinite
            timeline.autoReverse = true

            val kf1X = new JKeyValue(node.scaleXProperty(), originalScaleX)
            val kf1Y = new JKeyValue(node.scaleYProperty(), originalScaleY)
            val frame1 = new JKeyFrame(Duration.ZERO, null, null, java.util.List.of(kf1X, kf1Y))
            
            val kf2X = new JKeyValue(node.scaleXProperty(), originalScaleX * 1.1)
            val kf2Y = new JKeyValue(node.scaleYProperty(), originalScaleY * 1.1)
            val frame2 = new JKeyFrame(Duration(duration), null, null, java.util.List.of(kf2X, kf2Y))
            
            timeline.keyFrames = Seq(frame1, frame2)
            nodeAnimations.put(node, timeline)
            
            timeline.play()
      } 

      // ANIMATION - Shake Effect
      def applyShakeEffect(node: Node, duration: Double = 0.5, magnitude: Double = 10.0): Unit = {
            val originalX = node.translateX()
            
            val timeline = new Timeline()
            timeline.cycleCount = 4
            timeline.autoReverse = true
            
            val kf1 = new JKeyValue(node.translateXProperty(), originalX)
            val frame1 = new JKeyFrame(Duration.Zero, null, null, java.util.List.of(kf1))
            
            val kf2 = new JKeyValue(node.translateXProperty(), originalX - magnitude)
            val frame2 = new JKeyFrame(Duration(duration / 8), null, null, java.util.List.of(kf2))
            
            val kf3 = new JKeyValue(node.translateXProperty(), originalX + magnitude)
            val frame3 = new JKeyFrame(Duration(duration / 4), null, null, java.util.List.of(kf3))
            
            timeline.keyFrames = Seq(frame1, frame2, frame3)
            timeline.onFinished = _ => {
                  node.translateX = originalX
            }
            
            timeline.play()
      }

      // ANIMATION - Fade-in Effect 
      def applyFadeInEffect(node: Node, duration: Double = 1.0, delay: Double = 0.0): Unit = {
            node.opacity = 0
            
            val fadeIn = new FadeTransition()
            fadeIn.fromValue = 0.0
            fadeIn.toValue = 1.0
            fadeIn.duration = Duration(duration)
            fadeIn.node = node
            
            if (delay > 0) {
                  val pause = new PauseTransition(Duration(delay))
                  val sequence = new SequentialTransition()
                  sequence.children = Seq(pause, fadeIn)
                  sequence.play()
            } else {
                  fadeIn.play()
            }
      }

      // ANIMATION - Green Effect 
      def applySpinningGreenEffect(node: Node, duration: Double = 1.5): Unit = {
            val originalStyle = node.style.value
            val originalEffect = node.effect.value
            
            val greenGlow = new DropShadow()
            greenGlow.color = Color.Green
            greenGlow.radius = 25
            greenGlow.spread = 0.7
            
            node.effect = greenGlow
            node.style = originalStyle + "-fx-background-color: rgba(0, 255, 0, 0.2); -fx-border-color: green; -fx-border-width: 2px;"
            
            val rotate = new RotateTransition()
            rotate.duration = Duration(duration)
            rotate.byAngle = 360 
            rotate.node = node
            rotate.cycleCount = 1
            rotate.interpolator = JInterpolator.EASE_BOTH
            
            rotate.onFinished = _ => {
                  val restoreEffect = new PauseTransition(Duration(0.5))
                  restoreEffect.onFinished = _ => {
                  node.effect = originalEffect
                  node.style = originalStyle
                  }
                  restoreEffect.play()
            }
            
            rotate.play()
      }

      // ANIMATION - Remove Effect
      def removeEffect(node: Node): Unit = {
            nodeAnimations.get(node).foreach(_.stop())
            nodeAnimations.remove(node)
            node.effect = null
      
            node.scaleX = 1.0; node.scaleY = 1.0
            node.translateX = 0.0; node.translateY = 0.0
      }

      // ANIMATION - Highlight Effect
      def applyHighlightEffect(node: Node, highlightColor: Color = Color.Yellow, duration: Double = 1.0): Unit = {
            val originalEffect = node.effect.value
            
            val highlight = new DropShadow()
            highlight.color = highlightColor
            highlight.radius = 20
            highlight.spread = 0.7
            
            node.effect = highlight
            
            val pause = new PauseTransition(Duration(duration))
            pause.onFinished = _ => {
                  node.effect = originalEffect
            }
            
            pause.play()
      }
}