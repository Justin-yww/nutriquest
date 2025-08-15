file://<WORKSPACE>/src/main/scala/zeroHungerGame/core/Routes.scala
### java.lang.AssertionError: assertion failed

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file://<WORKSPACE>/src/main/scala/zeroHungerGame/core/Routes.scala
text:
```scala
package zeroHungerGame.core



object Routes { 
      // NOTE: Additional Screens can be added here
      // [1] Main Menu 
      case object Menu extends Screen {
            override def fxmlPath: Option[String] = Some("/zeroHungerGame/view/Menu.fxml")
            override def title: String = "Main Menu"
      }

      // [2] Educational Info Display Screen
      case class EduVillage(currentPage: Int = 0) extends Screen { 
            override def fxmlPath: Option[String] = Some("/zeroHungerGame/view/Education.fxml")
            override def title: String = "Sproutfiled - The Nutrition within the rural context"
      }

}
```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:11)
	dotty.tools.dotc.core.Annotations$LazyAnnotation.tree(Annotations.scala:139)
	dotty.tools.dotc.core.Annotations$Annotation$Child$.unapply(Annotations.scala:245)
	dotty.tools.dotc.typer.Namer.insertInto$1(Namer.scala:476)
	dotty.tools.dotc.typer.Namer.addChild(Namer.scala:487)
	dotty.tools.dotc.typer.Namer$Completer.register$1(Namer.scala:931)
	dotty.tools.dotc.typer.Namer$Completer.registerIfChildInCreationContext$$anonfun$1(Namer.scala:940)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.typer.Namer$Completer.registerIfChildInCreationContext(Namer.scala:940)
	dotty.tools.dotc.typer.Namer$Completer.complete(Namer.scala:829)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.completeFrom(SymDenotations.scala:174)
	dotty.tools.dotc.core.Denotations$Denotation.completeInfo$1(Denotations.scala:188)
	dotty.tools.dotc.core.Denotations$Denotation.info(Denotations.scala:190)
	dotty.tools.dotc.core.Types$NamedType.info(Types.scala:2363)
	dotty.tools.dotc.core.Types$TermLambda.dotty$tools$dotc$core$Types$TermLambda$$_$compute$1(Types.scala:3896)
	dotty.tools.dotc.core.Types$TermLambda.foldArgs$2(Types.scala:3903)
	dotty.tools.dotc.core.Types$TermLambda.dotty$tools$dotc$core$Types$TermLambda$$_$compute$1(Types.scala:4524)
	dotty.tools.dotc.core.Types$TermLambda.dotty$tools$dotc$core$Types$TermLambda$$depStatus(Types.scala:3923)
	dotty.tools.dotc.core.Types$TermLambda.dependencyStatus(Types.scala:3937)
	dotty.tools.dotc.core.Types$TermLambda.isResultDependent(Types.scala:3959)
	dotty.tools.dotc.core.Types$TermLambda.isResultDependent$(Types.scala:3853)
	dotty.tools.dotc.core.Types$MethodType.isResultDependent(Types.scala:3998)
	dotty.tools.dotc.typer.TypeAssigner.assignType(TypeAssigner.scala:295)
	dotty.tools.dotc.typer.TypeAssigner.assignType$(TypeAssigner.scala:16)
	dotty.tools.dotc.typer.Typer.assignType(Typer.scala:119)
	dotty.tools.dotc.ast.tpd$.Apply(tpd.scala:48)
	dotty.tools.dotc.ast.tpd$TreeOps$.appliedToTermArgs$extension(tpd.scala:966)
	dotty.tools.dotc.ast.tpd$.New(tpd.scala:544)
	dotty.tools.dotc.ast.tpd$.New(tpd.scala:535)
	dotty.tools.dotc.core.Annotations$Annotation$Child$.makeChildLater$1(Annotations.scala:234)
	dotty.tools.dotc.core.Annotations$Annotation$Child$.later$$anonfun$1(Annotations.scala:237)
	dotty.tools.dotc.core.Annotations$LazyAnnotation.tree(Annotations.scala:143)
	dotty.tools.dotc.core.Annotations$Annotation$Child$.unapply(Annotations.scala:245)
	dotty.tools.dotc.typer.Namer.insertInto$1(Namer.scala:476)
	dotty.tools.dotc.typer.Namer.addChild(Namer.scala:487)
	dotty.tools.dotc.typer.Namer$Completer.register$1(Namer.scala:931)
	dotty.tools.dotc.typer.Namer$Completer.registerIfChildInCreationContext$$anonfun$1(Namer.scala:940)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.typer.Namer$Completer.registerIfChildInCreationContext(Namer.scala:940)
	dotty.tools.dotc.typer.Namer$Completer.complete(Namer.scala:829)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.completeFrom(SymDenotations.scala:174)
	dotty.tools.dotc.core.Denotations$Denotation.completeInfo$1(Denotations.scala:188)
	dotty.tools.dotc.core.Denotations$Denotation.info(Denotations.scala:190)
	dotty.tools.dotc.core.SymDenotations$ClassDenotation.computeMembersNamed(SymDenotations.scala:2145)
	dotty.tools.dotc.core.SymDenotations$ClassDenotation.membersNamed(SymDenotations.scala:2115)
	dotty.tools.dotc.core.SymDenotations$ClassDenotation.findMember(SymDenotations.scala:2166)
	dotty.tools.dotc.core.Types$Type.go$1(Types.scala:740)
	dotty.tools.dotc.core.Types$Type.findMember(Types.scala:919)
	dotty.tools.dotc.typer.TypeAssigner.selectionType(TypeAssigner.scala:158)
	dotty.tools.dotc.typer.TypeAssigner.selectionType$(TypeAssigner.scala:16)
	dotty.tools.dotc.typer.Typer.selectionType(Typer.scala:119)
	dotty.tools.dotc.typer.Typer.typedSelectWithAdapt(Typer.scala:697)
	dotty.tools.dotc.typer.Typer.typeSelectOnTerm$1(Typer.scala:806)
	dotty.tools.dotc.typer.Typer.typedSelect(Typer.scala:843)
	dotty.tools.dotc.typer.Typer.typedNamed$1(Typer.scala:3120)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3231)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3302)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3306)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3417)
	dotty.tools.dotc.typer.Applications.realApply$1(Applications.scala:995)
	dotty.tools.dotc.typer.Applications.typedApply(Applications.scala:1191)
	dotty.tools.dotc.typer.Applications.typedApply$(Applications.scala:400)
	dotty.tools.dotc.typer.Typer.typedApply(Typer.scala:119)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3151)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3232)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3302)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3306)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3417)
	dotty.tools.dotc.typer.Typer.typedValDef(Typer.scala:2520)
	dotty.tools.dotc.typer.Typer.typedNamed$1(Typer.scala:3124)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3231)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3302)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3306)
	dotty.tools.dotc.typer.Typer.traverse$1(Typer.scala:3328)
	dotty.tools.dotc.typer.Typer.typedStats(Typer.scala:3374)
	dotty.tools.dotc.typer.Typer.typedClassDef(Typer.scala:2771)
	dotty.tools.dotc.typer.Typer.typedTypeOrClassDef$1(Typer.scala:3139)
	dotty.tools.dotc.typer.Typer.typedNamed$1(Typer.scala:3143)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3231)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3302)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3306)
	dotty.tools.dotc.typer.Typer.traverse$1(Typer.scala:3328)
	dotty.tools.dotc.typer.Typer.typedStats(Typer.scala:3374)
	dotty.tools.dotc.typer.Typer.typedPackageDef(Typer.scala:2914)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3184)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3232)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3302)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3306)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3417)
	dotty.tools.dotc.typer.TyperPhase.typeCheck$$anonfun$1(TyperPhase.scala:45)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:467)
	dotty.tools.dotc.typer.TyperPhase.typeCheck(TyperPhase.scala:51)
	dotty.tools.dotc.typer.TyperPhase.$anonfun$4(TyperPhase.scala:97)
	scala.collection.Iterator$$anon$6.hasNext(Iterator.scala:479)
	scala.collection.Iterator$$anon$9.hasNext(Iterator.scala:583)
	scala.collection.immutable.List.prependedAll(List.scala:152)
	scala.collection.immutable.List$.from(List.scala:685)
	scala.collection.immutable.List$.from(List.scala:682)
	scala.collection.IterableOps$WithFilter.map(Iterable.scala:900)
	dotty.tools.dotc.typer.TyperPhase.runOn(TyperPhase.scala:96)
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

java.lang.AssertionError: assertion failed