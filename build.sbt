name := "foo"

version := "0"

scalaVersion := "3.1.3-RC5"

classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat

libraryDependencies ++= Seq(
  "org.scala-lang" %% "scala3-compiler" % scalaVersion.value)
