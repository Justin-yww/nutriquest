file://<WORKSPACE>/src/main/scala/zeroHungerGame/game/DragDropService.scala.scala
### java.lang.StringIndexOutOfBoundsException: Range [982, 988) out of bounds for length 985

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 982
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

      ..@@

}
```



#### Error stacktrace:

```
java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55)
	java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210)
	java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98)
	java.base/jdk.internal.util.Preconditions.outOfBoundsCheckFromToIndex(Preconditions.java:112)
	java.base/jdk.internal.util.Preconditions.checkFromToIndex(Preconditions.java:349)
	java.base/java.lang.String.checkBoundsBeginEnd(String.java:4914)
	java.base/java.lang.String.substring(String.java:2876)
	dotty.tools.pc.completions.CompletionProvider.mkItem$1(CompletionProvider.scala:248)
	dotty.tools.pc.completions.CompletionProvider.completionItems(CompletionProvider.scala:347)
	dotty.tools.pc.completions.CompletionProvider.$anonfun$1(CompletionProvider.scala:149)
	scala.collection.immutable.List.map(List.scala:247)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:141)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: Range [982, 988) out of bounds for length 985