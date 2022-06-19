name := "foo"

version := "0"

scalaVersion := "3.1.3-RC5"

libraryDependencies ++= Seq(
  "org.scala-lang" %% "scala3-compiler" % scalaVersion.value)
