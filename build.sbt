ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "final-project-Justin-yww",

    // --- Pick the correct JavaFX native classifier for your OS/CPU ---
    libraryDependencies ++= {
      val os   = sys.props.getOrElse("os.name", "").toLowerCase
      val arch = sys.props.getOrElse("os.arch", "").toLowerCase

      val classifier =
        if (os.contains("mac") && (arch.contains("aarch64") || arch.contains("arm64"))) "mac-aarch64"
        else if (os.contains("mac")) "mac"
        else if (os.contains("win") && (arch.contains("aarch64") || arch.contains("arm64"))) "win-aarch64"
        else if (os.contains("win")) "win"
        else if (os.contains("linux") && (arch.contains("aarch64") || arch.contains("arm64"))) "linux-aarch64"
        else if (os.contains("linux")) "linux"
        else throw new Exception(s"Unknown platform: $os / $arch")

      val fxVersion = "21.0.5"

      // Minimal set (uncomment what you need)
      val fxModules = Seq("base", "graphics", "controls" /*, "fxml", "media", "swing", "web"*/)

      fxModules.map(m => "org.openjfx" % s"javafx-$m" % fxVersion classifier classifier)
    },

    // ScalaFX 21 for Scala 3
    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",

    // Your DB libraries (unchanged)
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % "4.3.0",
    )
  )
