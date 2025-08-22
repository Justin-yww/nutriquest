file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala
### java.lang.AssertionError: assertion failed: position error, parent span does not contain child span
parent      = new FoodItem("Fish", 180,
  Set("protein",
    "),               " images / foods / fish _root_.scala.Predef.???)
) # -1,
parent span = <4284..6333>,
child       = Set("protein", "),               " images / foods / fish _root_.scala.Predef.???
  ) # -1,
child span  = [4316..4319..6346]

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/model/FoodItem.scala
text:
```scala
package zeroHungerGame.model

import scalafx.beans.property.{BooleanProperty, IntegerProperty, ObjectProperty, StringProperty}
import scalafx.scene.image.Image
import scala.collection.mutable

import zeroHungerGame.util.ResourceLoader

class FoodItem(
      initialName: String,
      initialCalories: Int,
      initialTags: Set[String],
      initialImagePath: String
){
      private val _name       = StringProperty(initialName)
      private val _calories   = IntegerProperty(initialCalories)
      private val _tags       = ObjectProperty[Set[String]](initialTags)
      private val _imagePath  = StringProperty(initialImagePath)
      private val _imageCache = ObjectProperty[Image](
      new Image(ResourceLoader.loadImagePath("placeholder.png"))
      )
      private val _isMatched  = BooleanProperty(false)

      def name: String            = _name.value
      def calories: Int           = _calories.value
      def tags: Set[String]       = _tags.value
      def imagePath: String       = _imagePath.value
      def isMatched: Boolean      = _isMatched.value

      def name_=(v: String): Unit       = _name.value = v
      def calories_=(v: Int): Unit      = _calories.value = v
      def tags_=(v: Set[String]): Unit  = _tags.value = v
      def isMatched_=(v: Boolean): Unit = _isMatched.value = v

      def nameProperty      = _name
      def caloriesProperty  = _calories
      def tagsProperty      = _tags
      def isMatchedProperty = _isMatched

      // Get image from cache
      def image: Image = {
            val relPath = imagePath.stripPrefix("images/")  
            val url     = ResourceLoader.loadImagePath(relPath)
            val safe    =
                  if (url == null || url.isEmpty || url.startsWith("data:"))
                  ResourceLoader.loadImagePath("placeholder.png")
                  else url
            val img = new Image(safe)
            _imageCache.value = img
            _imageCache.value
      }

      def hasTag(tag: String): Boolean = tags.contains(tag)
      def satisfies(requiredTags: Set[String]): Boolean = requiredTags.subsetOf(tags)

      // PURPOSE: Get the primary nutritional category (BASED ON PROMPT)
      def primaryCategory: String = {
            // Extract primary category with food name
            name.toLowerCase match {
                  // Village Mode
                  case "rice" => "Carbs"
                  case "milk" => "Protein"
                  case "fish" => "Protein"
                  case "vegetables" => "Fiber"
                  case "beans" => "Protein"

                  // Urban Mode
                  case "orange" => "Vitamin"
                  case "chicken" => "Protein"
                  case "fish_urban" => "omega-3"
                  case "banana" => "Potassium"
                  case "mixed nuts" => "Healthy Fats"
                  case "yogurt" => "Calcium"
                  case "whole-grain bread" => "Fiber"
                  
                  case _ => 
                        val priorities = List(
                              "carbs", "protein", "fiber", "vitamin", "calcium", 
                              "omega-3", "potassium", "healthy fats", "dairy", 
                              "fruit", "vegetable", "grain", "meat"
                        )

                        priorities.find(priority => tags.exists(_.toLowerCase.replace("-", " ") == priority.toLowerCase.replace("-", " ")))
                              .getOrElse(tags.headOption.getOrElse("Mixed"))
                              .split(" ").map(_.capitalize).mkString(" ")
            }
      }

      override def toString: String =
      s"FoodItem($name, calories=$calories, tags=[${tags.mkString(", ")}])"

}

object FoodItem { 
      private val foodDatabase = mutable.Map[String, FoodItem]()

      def create(foodType: String): FoodItem = {
            foodType.toLowerCase.replace(" ", "").replace("-", "") match {
                  // Village Mode Items 
                  case "rice"       => new FoodItem("Rice",       200, Set("carbs", "grain", "energy"),         "images/foods/rice.png")
                  case "milk"       => new FoodItem("Milk",       120, Set("protein", "calcium", "dairy"),      "images/foods/milk.png")
                  case "fish"       => new FoodItem("Fish",       180, Set("protein", "),               "images/foods/fish.png")
                  case "vegetables" => new FoodItem("Vegetables",  40, Set("fiber", "vitamin", "vegetable"),    "images/foods/vegetables.png")
                  case "beans"      => new FoodItem("Beans",      150, Set("protein", "fiber", "folate"),       "images/foods/beans.png")

                  // Urban Mode Items
                  case "orange"           => new FoodItem("Orange",      80, Set("vitamin", "fruit", "energy"),       "images/foods/orange.png")
                  case "chicken"          => new FoodItem("Chicken",    250, Set("protein", "meat"),                  "images/foods/chicken.png")
                  case "fish_urban"       => new FoodItem("Fish",       180, Set("omega-3"),               "images/foods/fish_urban.png")
                  case "banana"           => new FoodItem("Banana",     105, Set("potassium", "fruit", "quick-energy"), "images/foods/banana.png")
                  case "mixednuts"        => new FoodItem("Mixed Nuts", 180, Set("healthy fats", "protein", "focus"), "images/foods/mixed_nuts.png")
                  case "yogurt"           => new FoodItem("Yogurt",     100, Set("calcium", "protein", "dairy"),      "images/foods/yogurt.png")
                  case "wholegrainbread"  => new FoodItem("Whole-Grain Bread", 120, Set("fiber", "carbs", "grain"), "images/foods/whole_grain_bread.png")
                  
                  // Handling Variations 
                  case foodname if foodname.contains("mixed") && foodname.contains("nuts") => new FoodItem("Mixed Nuts", 180, Set("healthy fats", "protein", "focus"), "images/foods/mixed_nuts.png")
                  case foodname if foodname.contains("whole") && foodname.contains("grain") => new FoodItem("Whole-Grain Bread", 120, Set("fiber", "carbs", "grain"), "images/foods/whole_grain_bread.png")

                  case _            => new FoodItem(foodType.split(" ").map(_.capitalize).mkString(" "), 100, Set("unknown"), "images/placeholder.png")
            }
      }

      def get(foodType: String): FoodItem =
            foodDatabase.getOrElseUpdate(foodType, create(foodType))   
}
```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:175)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.parsing.Parser.parse$$anonfun$1(ParserPhase.scala:39)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:467)
	dotty.tools.dotc.parsing.Parser.parse(ParserPhase.scala:40)
	dotty.tools.dotc.parsing.Parser.$anonfun$2(ParserPhase.scala:52)
	scala.collection.Iterator$$anon$6.hasNext(Iterator.scala:479)
	scala.collection.Iterator$$anon$9.hasNext(Iterator.scala:583)
	scala.collection.immutable.List.prependedAll(List.scala:152)
	scala.collection.immutable.List$.from(List.scala:685)
	scala.collection.immutable.List$.from(List.scala:682)
	scala.collection.IterableOps$WithFilter.map(Iterable.scala:900)
	dotty.tools.dotc.parsing.Parser.runOn(ParserPhase.scala:51)
	dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:315)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1324)
	dotty.tools.dotc.Run.runPhases$1(Run.scala:308)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:348)
	dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:357)
	dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:69)
	dotty.tools.dotc.Run.compileUnits(Run.scala:357)
	dotty.tools.dotc.Run.compileSources(Run.scala:261)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:161)
	dotty.tools.pc.CachingDriver.run(CachingDriver.scala:45)
	dotty.tools.pc.WithCompilationUnit.<init>(WithCompilationUnit.scala:31)
	dotty.tools.pc.SimpleCollector.<init>(PcCollector.scala:351)
	dotty.tools.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:63)
	dotty.tools.pc.PcSemanticTokensProvider.Collector$lzyINIT1(PcSemanticTokensProvider.scala:63)
	dotty.tools.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:63)
	dotty.tools.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:88)
	dotty.tools.pc.ScalaPresentationCompiler.semanticTokens$$anonfun$1(ScalaPresentationCompiler.scala:111)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: position error, parent span does not contain child span
parent      = new FoodItem("Fish", 180,
  Set("protein",
    "),               " images / foods / fish _root_.scala.Predef.???)
) # -1,
parent span = <4284..6333>,
child       = Set("protein", "),               " images / foods / fish _root_.scala.Predef.???
  ) # -1,
child span  = [4316..4319..6346]