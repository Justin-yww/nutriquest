
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "introtosclafx",
    libraryDependencies ++= {
      val os = System.getProperty("os.name")
      val arch = System.getProperty("os.arch")
      // Determine OS version of JavaFX binaries
      val classifier = (os, arch) match {
        case ("Linux", "aarch64") => "linux-aarch64"
        case ("Linux", _)         => "linux-x64"
        case ("Mac OS X", "aarch64") => "mac-aarch64"
        case ("Mac OS X", _)      => "mac-x64"
        case ("Windows", "amd64") => "win-x64"
        case ("Windows", _)       => "win-x86"
        case _                    => throw new Exception("Unknown platform!")
      }
      
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "21.0.4" classifier osName)
    },
    libraryDependencies ++= Seq("org.scalafx" %% "scalafx" % "21.0.0-R32")
  )
//enable for sbt-assembly
//assembly / assemblyMergeStrategy := {
//  case PathList("META-INF", xs @ _*) => MergeStrategy.discard // Discard all META-INF files
//  case PathList("reference.conf")    => MergeStrategy.concat  // Concatenate config files
//  case PathList(ps @ _*) if ps.last.endsWith(".class") => MergeStrategy.first // Take the first class file
//  case _ => MergeStrategy.first // Apply first strategy to any other file
//}
