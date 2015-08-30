lazy val root = (project in file(".")).settings(
  name := "jsAst",
  version := "0.0.0",
  scalaVersion := "2.11.7",
  libraryDependencies ++= Seq(
    "org.mozilla" % "rhino" % "1.7.7"
  )
)


