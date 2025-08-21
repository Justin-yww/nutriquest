file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala
### java.lang.StringIndexOutOfBoundsException: Range [809, 815) out of bounds for length 811

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 809
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala
text:
```scala
package zeroHungerGame.model

import scalafx.beans.property.{IntegerProperty, ObjectProperty}
import scala.collection.mutable.Map

class NutritionProfile(
      initialRequiredTags: Set[String], 
      initialDailyCalories: Int, 
      val primaryNeed: String
){
      // Properties for data binding 
      private val _requiredTags = ObjectProperty[Set[String]](initialRequiredTags)
      private val _dailyCalories = IntegerProperty(initialDailyCalories)
      private val _fulfilledTags = ObjectProperty[Map[String, Boolean]](
            initialRequiredTags.map(tag => tag -> false).to(Map)
      )

      // Getters
      def requiredTags: Set[String] = _requiredTags.value
      def dailyCalories: Int = _dailyCalories.value
      def fulfilledTags: Map[String, Boolean] = _fulfilledTags.value

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

java.lang.StringIndexOutOfBoundsException: Range [809, 815) out of bounds for length 811