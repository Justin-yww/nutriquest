ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

/*  
AI DECLARATION: Usage for debugging
  the build.sbt file was modified heavily using Claude Sonnet 3.7 as there was a 
  lot of error when running trying 'sbt run' in the VS Code terminal. The provided
  build.sbt in the practical classes faced several compilation errors which was 
  then debugged with Claude Sonnet 3.7
*/ 

lazy val root = (project in file("."))
  .settings(
    name := "final-project-Justin-yww",
    fork := true,

    // JavaFX Modules Selection by platform classifier
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
      Seq("base", "graphics", "controls", "fxml", "media" ).map(m => "org.openjfx" % s"javafx-$m" % fxVersion classifier platform)
    },

    Compile / unmanagedResourceDirectories += baseDirectory.value / "src" / "main" / "resources",
    Compile / run / javaOptions ++= Seq("--enable--native-access=ALL-UNAMED"),
    Compile / mainClass := Some("zeroHungerGame.Main"),
    Compile / run / mainClass := Some("zeroHungerGame.Main")
  )
