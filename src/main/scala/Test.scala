package foo

import dotty.tools.repl.{ ReplDriver, ReplCompiler, State }

object Test {

  val classpath = getClass.getClassLoader match {
    case cls: java.net.URLClassLoader =>
      cls.getURLs.toSeq.collect {
        case url if url.getProtocol == "file" =>
          url.toString.stripPrefix("file:")
      }.mkString(":")

    case _ =>
      ""
  }

  /*
  val classpath =
    "/Users/cchantep/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.1.3-RC5/scala3-library_3-3.1.3-RC5.jar"
   */

  println(classpath)

  val replDriver = new ReplDriver(
    Array("-classpath", classpath),
    classLoader = Some(getClass.getClassLoader)
  )

  val initialState: State = replDriver.initialState

  val replCompiler = new ReplCompiler

  def newRun(state: State): State = {
    val run = replCompiler.newRun(state.context.fresh, state)
    state.copy(context = run.runContext)
  }

  val state: State = newRun(initialState)

  def main(args: Array[String]): Unit = {
    replCompiler.typeCheck("case class TestClass1(name: String)")(using state)
  }
}
