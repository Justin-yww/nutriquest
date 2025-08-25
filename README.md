# Project NutriQuest

## SETUP GUIDE: Scala on ARM-based Macbooks / Macs

This document provides a complete procedure for setting up and running the Scala program on an ARM-based Macs.

The build environment as per assignment brief

1. **SBT Builder**
2. **Java 21 Library**
3. **Scala 3 Library**
4. **ScalaFX 21 GUI Library**

---

## 1. Prerequisites — Install Java 21 using Temurin and SBT

Open a terminal and run the following commands:

```bash
# Install Java 21 (Temurin build)
brew install --cask temurin@21
```

```bash
# Install SBT
brew install sbt
```

To verify CPU architecture (Expected output: arm64)

```bash
arch
```

## 2. Configure Java 21 for the current session

Set JAVA_HOME to JDK 21:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

You may also check the Java version (Should indicate Java 21)

```bash
java -version
```

## 3. Setting up Visual Studio Code

- Install visual studio code if you have not
- Open the extentions panel and install the following:
  1. Metals
  2. Scala Syntax (OPTIONAL - but highly encouraged)

## 4. Running the program

Just input the following into the VS code terminal to run the program:

```bash
sbt run
```

## Additional

- If there were previously downloaded JavaFX binaries with the wrong architecture (Intel-based), remove them:

```bash
rm -rf ~/.openjfx/cache
```
