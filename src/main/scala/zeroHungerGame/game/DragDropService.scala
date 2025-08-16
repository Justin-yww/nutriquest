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
            onInvalidDrop: (FoodItem, Character) => Unit = (_, _) => {}
      ): Unit = { 
            dropTargets(node) = character

            // DRAG OVER EVENT
            node.onDragOver = (event: DragEvent) => {
                  if (event.getDragboard.hasContent(FOOD_ID_KEY)) {
                        
                        event.acceptTransferModes(TransferMode.Move)
                        
                        val foodName = event.getDragboard.getContent(FOOD_ID_KEY).toString
                        val foodOpt = findFoodByName(foodName)
                        
                        foodOpt.foreach { food =>
                        if (character.matchesFood(food)) {
                              node.effect = validTargetEffect
                        } else {
                              node.effect = invalidTargetEffect
                        }
                  }
            }
            
            event.consume()
      }

            // HANDLE EXIT EVENT
            node.onDragExited = (event: DragEvent) => {
                  node.effect = null
                  event.consume()
            }

            // HANDLE DROP EVENT
            node.onDragDropped = (event: DragEvent) => {
                  val db = event.getDragboard
                  var success = false
                  
                  if (db.hasContent(FOOD_ID_KEY)) {
                  val foodName = db.getContent(FOOD_ID_KEY).toString
                  val foodOpt = findFoodByName(foodName)
                  
                  foodOpt.foreach { food =>
                  if (character.matchesFood(food)) {
                        onValidDrop(food, character)
                        success = true
                  } else {
                        onInvalidDrop(food, character)
                  }
            }
      }
                  
                  node.effect = null
                  
                  event.setDropCompleted(success)
                  event.consume()
            }
      }

      private def findFoodByName(name: String): Option[FoodItem] = {
            dragSources.values.find(_.name == name)
      }

      def setupSnapping(
            node: Node, 
            parent: Pane,
            snapPoints: List[Point2D],
            snapRadius: Double = 30.0
      ): Unit = { 
            var mouseAnchorX = 0.0
            var mouseAnchorY = 0.0

            node.onMousePressed = (event: MouseEvent) => {
                  mouseAnchorX = event.getSceneX
                  mouseAnchorY = event.getSceneY
                  event.consume()
            }

            node.onMouseDragged = (event: MouseEvent) => {
                  val deltaX = event.getSceneX - mouseAnchorX
                  val deltaY = event.getSceneY - mouseAnchorY
                  
                  val newX = node.getLayoutX + deltaX
                  val newY = node.getLayoutY + deltaY
                  
                  mouseAnchorX = event.getSceneX
                  mouseAnchorY = event.getSceneY
                  
                  node.setLayoutX(newX)
                  node.setLayoutY(newY)
                  
                  event.consume()
            }

            node.onMouseReleased = (event: MouseEvent) => {
                  val nodeBounds = node.getBoundsInParent
                  val centerX = nodeBounds.getMinX + nodeBounds.getWidth / 2
                  val centerY = nodeBounds.getMinY + nodeBounds.getHeight / 2
                  
                  val nearestSnapPoint = findNearestSnapPoint(
                        new Point2D(centerX, centerY), 
                        snapPoints, 
                        snapRadius
                  )
                  
                  nearestSnapPoint.foreach { point =>
                        val snapX = point.x - nodeBounds.getWidth / 2
                        val snapY = point.y - nodeBounds.getHeight / 2
                  
                  
                        node.setLayoutX(snapX)
                        node.setLayoutY(snapY)
                  }
                  
                  event.consume()
            }
      }

      private def findNearestSnapPoint(
            point: Point2D, 
            snapPoints: List[Point2D],
            snapRadius: Double
      ): Option[Point2D] = {
            snapPoints
                  .map(p => (p, p.distance(point)))
                  .filter(_._2 <= snapRadius)
                  .minByOption(_._2)
                  .map(_._1)
      }

      def nodesIntersect(node1: Node, node2: Node): Boolean = {
            val bounds1 = node1.localToScene(node1.getBoundsInLocal)
            val bounds2 = node2.localToScene(node2.getBoundsInLocal)
            
            bounds1.intersects(bounds2)
      }
}

object DragDropService {
      val FOOD_ID_KEY = new JDataFormat("food-id")
}