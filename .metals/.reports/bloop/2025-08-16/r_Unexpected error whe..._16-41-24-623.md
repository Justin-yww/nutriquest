error id: m0u7Hq8QAtkNPM/FtPBVuQ==
### Bloop error:

Unexpected error when compiling root: java.io.IOException: read error
	at dotty.tools.io.AbstractFile.toByteArray(AbstractFile.scala:180)
	at dotty.tools.dotc.util.SourceFile$.apply(SourceFile.scala:280)
	at dotty.tools.dotc.core.Contexts$.dotty$tools$dotc$core$Contexts$Context$$_$getSource$$anonfun$1(Contexts.scala:257)
	at dotty.tools.dotc.util.GenericHashMap.getOrElseUpdate(GenericHashMap.scala:134)
	at dotty.tools.dotc.core.Contexts$Context.getSource(Contexts.scala:257)
	at dotty.tools.dotc.Run.compile$$anonfun$1(Run.scala:246)
	at scala.collection.immutable.List.map(List.scala:247)
	at dotty.tools.dotc.Run.compile(Run.scala:246)
	at dotty.tools.dotc.Driver.doCompile(Driver.scala:37)
	at dotty.tools.xsbt.CompilerBridgeDriver.run(CompilerBridgeDriver.java:141)
	at dotty.tools.xsbt.CompilerBridge.run(CompilerBridge.java:22)
	at sbt.internal.inc.AnalyzingCompiler.compile(AnalyzingCompiler.scala:91)
	at sbt.internal.inc.bloop.internal.BloopHighLevelCompiler.compileSources$1(BloopHighLevelCompiler.scala:148)
	at sbt.internal.inc.bloop.internal.BloopHighLevelCompiler.$anonfun$compile$9(BloopHighLevelCompiler.scala:181)
	at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:23)
	at sbt.internal.inc.bloop.internal.BloopHighLevelCompiler.$anonfun$compile$1(BloopHighLevelCompiler.scala:73)
	at bloop.tracing.NoopTracer$.trace(BraveTracer.scala:53)
	at sbt.internal.inc.bloop.internal.BloopHighLevelCompiler.timed$1(BloopHighLevelCompiler.scala:72)
	at sbt.internal.inc.bloop.internal.BloopHighLevelCompiler.$anonfun$compile$8(BloopHighLevelCompiler.scala:181)
	at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:23)
	at monix.eval.internal.TaskRunLoop$.startFull(TaskRunLoop.scala:81)
	at monix.eval.internal.TaskRestartCallback.syncOnSuccess(TaskRestartCallback.scala:101)
	at monix.eval.internal.TaskRestartCallback.onSuccess(TaskRestartCallback.scala:74)
	at monix.eval.internal.TaskExecuteOn$AsyncRegister$$anon$1.run(TaskExecuteOn.scala:71)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1717)
	at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1641)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1491)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:2073)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2035)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:187)
#### Short summary: 

Unexpected error when compiling root: java.io.IOException: read error
	at dotty.tools.io.AbstractFil...