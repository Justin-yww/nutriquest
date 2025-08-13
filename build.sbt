ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "final-project-Justin-yww",

    // Allows the selection of the JavaFX version based on the OS and architecture
    libraryDependencies ++= {
      val os   = sys.props.getOrElse("os.name", "").toLowerCase
      val arch = sys.props.getOrElse("os.arch", "").toLowerCase

      // Pick the correct JavaFX native classifier for your OS/CPU 
      // NOTE: The mac version is need to be identified as 'arm64' for the M1 based Macbooks
      val classifier =
        if (os.contains("mac") && (arch.contains("aarch64") || arch.contains("arm64"))) "mac-aarch64"
        else if (os.contains("mac")) "mac"
        else if (os.contains("win") && (arch.contains("aarch64") || arch.contains("arm64"))) "win-aarch64"
        else if (os.contains("win")) "win"
        else if (os.contains("linux") && (arch.contains("aarch64") || arch.contains("arm64"))) "linux-aarch64"
        else if (os.contains("linux")) "linux"
        else throw new Exception(s"Unknown platform: $os / $arch")

      // Version to use (MUST BE 21)
      val fxVersion = "21.0.5"

      // Modules needed for the GUI system 
      val fxModules = Seq("base", "graphics", "controls", "fxml" ) 

      fxModules.map(m => "org.openjfx" % s"javafx-$m" % fxVersion classifier classifier)
    },

    // ScalaFX 21 for Scala 3
    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",

    // Database libraries -- Not sure if used yet (FROM PRACTICAL)
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % "4.3.0",
      "com.h2database" % "h2" % "2.2.224",
      "org.apache.derby" % "derby" % "10.17.1.0",
      "org.apache.derby" % "derbytools" % "10.17.1.0"
    ),

    // For JSON handling 
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.6" 
  )
