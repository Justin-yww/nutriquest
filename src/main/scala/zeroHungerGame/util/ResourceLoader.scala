package zeroHungerGame.util

import scala.util.{Try, Success, Failure}
import scala.collection.mutable
import scala.io.Source
import java.net.URL
import java.io.InputStream

object ResourceLoader {

      // Base Resource path - help locate resources within the project
      private val basePath = "/zeroHungerGame"

      def init(): Unit = {
            if (!initialized){
                  println("Initialising ResourceLoader")

                  // DEBUG: List available resources 
                  debugResourceStructure()

                  // Preload essential resources
                  Try {
                        // PART 1: Load the CSS
                        val cssPath = loadCssPath("style.css")
                        println(s"CSS loaded at: $cssPath")

                        // PART 2: Load the Images
                        loadImagePath("app_icon.png")
                        loadImagePath("nutriquest.png")
                        loadImagePath("placeholder.png")
                  } match {
                        case Success(_) => 
                              println("ResourceLoader initialized successfully")
                        case Failure(e) => 
                              println(s"Warning: ResourceLoader initialization incomplete - ${e.getMessage}")
                  }

                  initialized = true 
            }
      }

      // DEBUGGING METHOD: 
      private def debugResourceStructure(): Unit = {
            println("=== Resources Structure Debug ===")

            // Check for CSS Directory 
            val cssUrl = getClass.getResource(s"$basePath/css")
            println(s"CSS directory URL: $cssUrl")

            // Check for specific style.css file
            val styleURL = getClass.getResource(s"$basePath/css/style.css")
            println(s"style.css URL: $styleURL")

            // Check alternative paths
            val altStyleUrl = getClass.getResource("/zeroHungerGame/css/style.css")
            println(s"Alternative style.css URL: $altStyleUrl")

            val directStyleUrl = getClass.getResource("/css/style.css")
            println(s"Direct style.css URL: $directStyleUrl")

            println("=== End Resources Debug ===")
      }


      // FIND RESOURCE URL 
      def loadResourceURL(path: String): URL = {
            urlCache.getOrElseUpdate(path, {
                  val fullPath = s"$basePath/$path"
                  val url = getClass.getResource(fullPath)

                  if (url == null) {
                        throw new RuntimeException(s"Resource not found: $fullPath")
                  }

                  url
            })
      }

      // LOACATE IMAGE PATH 
      def loadImagePath(imagePath: String): String = { 
            // Try multiple paths to find the image 
            val possiblePaths = List(
                  s"$basePath/images/$imagePath",           // Standard path 
                  s"/zeroHungerGame/images/$imagePath",     // Path without leading slash 
                  s"/images/$imagePath",                    // Show direct images path
                  imagePath                                 // Show direct file
            )

            // DEBUG 
            println(s"Looking for image: $imagePath")

            // Try each path 
            for (path <- possiblePaths) {
                  val url = getClass.getResource(path)
                  println(s"Trying path: $path -> ${if (url != null) "FOUND" else "NOT FOUND"}")
                  if (url != null) {
                        return url.toString
                  }
            }

            val fallbackFile = new java.io.File(s"src/main/resources/zeroHungerGame/images/$imagePath")
            if (fallbackFile.exists()) {
                  println(s"Found image file at: ${fallbackFile.getAbsolutePath}")
                  fallbackFile.toURI.toString
            }else {
                  println(s"Warning: Image not found: $imagePath, using built-in default")
            }

      }

      def loadCssPath(cssPath: String): String = {
            // Try multiple paths to find the CSS file
            val possiblePaths = List(
                  s"$basePath/css/$cssPath",           // Standard path
                  s"/zeroHungerGame/css/$cssPath",     // Path without leading slash
                  s"/css/$cssPath",                    // Direct css path
                  cssPath                              // Direct file
            )

            // DEBUG 
            println(s"Looking for CSS: $cssPath")

            // Try each path 
            for (path <- possiblePaths) {
                  val url = getClass.getResource(path)
                  println(s"Trying css path: $path -> ${if (url != null) "FOUND" else "NOT FOUND"}")
                  if (url != null) {
                        println(s"Found CSS at: $path")
                        return url.toString
                  }
            }

            val fallbackFile = new java.io.File(s"src/main/resources/zeroHungerGame/css/$cssPath")
            if (fallbackFile.exists()) {
                  println(s"Found CSS file at: ${fallbackFile.getAbsolutePath}")
                  fallbackFile.toURI.toString
            }else {
                  println(s"ERROR: CSS not found: $cssPath")
            }
      }

      def clearCaches(): Unit = {
            urlCache.clear()
      }
} 
