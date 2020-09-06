ThisBuild / libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
)

ThisBuild / fork := true

ThisBuild / scalaVersion := "2.12.12"

lazy val kamon1Repro = project
  .enablePlugins(JavaAgent)
  .settings(
    libraryDependencies ++= List(
      "io.kamon" %% "kamon-core" % "1.1.6",
      "io.kamon" %% "kamon-scala-future" % "1.1.0"
    ),
    javaAgents += "org.aspectj" % "aspectjweaver" % "1.9.6" % Test,
    javaOptions ++= List("--add-opens", "java.base/java.lang=ALL-UNNAMED")
  )

lazy val kamon2Repro = project
  .enablePlugins(JavaAgent)
  .settings(
    libraryDependencies ++= List(
      "io.kamon" %% "kamon-bundle" % "2.1.6",
      "org.slf4j" % "slf4j-simple" % "1.7.30" % Test
    ),
    javaAgents +=  "io.kamon" % "kanela-agent" % "1.0.6" % Test
  )
