package foo

import dotty.tools.repl.{ ReplDriver, ReplCompiler, State }

import dotty.tools.dotc.core.{ Contexts, Symbols, Types }

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

  implicit def defaultCtx: Contexts.Context = state.context

  val tpe: Types.Type =
    Symbols.requiredClassRef(classOf[AnyValChild].getName)

  case class AnyValChild(value: String) extends AnyVal

  def main(args: Array[String]): Unit = {
    println(s"tpe = ${tpe.typeSymbol.isValueClass} / ${tpe.classSymbol.asClass.classDenot.superClass}")
    /*
     Expected: "true / class AnyVal"
     Actual: "tpe = false / val <none>"

     Workaround is to move `AnyValChild` outside the `Test` object
     */
  }
}
