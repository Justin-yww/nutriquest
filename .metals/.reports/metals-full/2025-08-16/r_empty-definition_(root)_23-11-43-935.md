error id: file://<WORKSPACE>/src/main/scala/zeroHungerGame/game/DragDropService.scala.scala:`<none>`.
file://<WORKSPACE>/src/main/scala/zeroHungerGame/game/DragDropService.scala.scala
empty definition using pc, found symbol in pc: `<none>`.
semanticdb not found
empty definition using fallback
non-local guesses:
	 -FoodItem.
	 -FoodItem#
	 -FoodItem().
	 -scala/Predef.FoodItem.
	 -scala/Predef.FoodItem#
	 -scala/Predef.FoodItem().
offset: 2598
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/game/DragDropService.scala.scala
text:
```scala
package zeroHungerGame.game

import scalafx.Includes._
import scalafx.scene.input.{ClipboardContent, DragEvent, Dragboard, MouseEvent, TransferMode}
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color
import scalafx.scene.layout.Pane
import scalafx.scene.Node
import scalafx.geometry.{Point2D, Rectangle2D}
import scala.collection.mutable
import javafx.scene.input.{DataFormat => JDataFormat}

import zeroHungerGame.model.{Character, FoodItem}

class DragDropService {
      // The data transfer key for drag operations 
      import DragDropService.FOOD_ID_KEY

      // SOURCES > TARGETS 
      private val dragSources = mutable.Map[Node, FoodItem]()
      private val dropTargets = mutable.Map[Node, Character]()

      // EFFECTS FOR DRAG N DROP 
      private val dragEffect = new DropShadow(10, Color.Gold)
      private val validTargetEffect = new DropShadow(15, Color.Green)
      private val invalidTargetEffect = new DropShadow(15, Color.Red)

      // GAME GUIDANCE HINTS
      private val dragHint = "Drag this food item to a character who needs it"
      private val validDropHint = "This character needs this nutrient - drop here!"
      private val invalidDropHint = "This character doesn't need this nutrient"

      def registerDragSource(
            node: Node, 
            food: FoodItem,
            onDragStarted: FoodItem => Unit = _ => {},
            onDragEnded: FoodItem => Unit = _ => {}
      ): Unit = {
            dragSources(node) = food

            // DRAG DETECTION 
            node.onDragDetected = (event: MouseEvent) => {
                  if (!food.isMatched) {  
                  val db: Dragboard = node.startDragAndDrop(TransferMode.Move)
                  
                  val content = new ClipboardContent()
                  content.putString(food.name)
                  
                  content.put(FOOD_ID_KEY, food.name)
                  
                  db.setContent(content)
                  
                  node.effect = dragEffect
                  
                  onDragStarted(food)
                  }
                  
                  event.consume()
            }

            // HANDLE DONE EVENT 
            node.onDragDone = (event: DragEvent) => {

                  node.effect = null
                  
                  onDragEnded(food)
                  
                  event.consume()
            }
      }

      def registerDropTarget(
            node: Node, 
            character: Character,
            onValidDrop: (FoodItem, Character) => Unit,
            onInvalidDrop: (Fo@@odItem, Character) => Unit = (_, _) => {}
      )
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.