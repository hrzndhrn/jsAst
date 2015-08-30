import rhino._

import scala.io.Source

object main {
  def main(args: Array[String]) = {

    val fileName: Option[String] = args.toList match {
      case name :: Nil => Some(name)
      case _ => None
    }

    val source = sourceCode(fileName)
    val astRoot = parser.parse(source)

    println("=== DebugVisitor ===")
    astRoot.visitAll(new DebugVisitor)
    println("")

    println("=== debugPrint ===")
    println(astRoot.debugPrint)

    println("=== nodeToListVisitor ===")
    val nodeToListVisitor = new NodeToListVisitor
    astRoot.visitAll(nodeToListVisitor)
    nodeToListVisitor.getNodes.foreach(node => {
      val indent = " " * node.depth()
      println(f"${node.getAbsolutePosition}%04d-${node.getLength}%03d|" +
        f"${indent}${node.getClass.getSimpleName}")
    })
    println("")

    println("=== source ===")
    println(source)
  }

  def sourceCode(fileName: Option[String]): String = fileName match {
      case Some(name) => Source.fromFile(name).getLines.mkString("\n")
      case _ =>
        """
          |var x = 1;
          |
          |// Function add
          |function inc(value) {
          |  // increase a value
          |  return value + 1;
          |}
          |
          |add(x);
        """.stripMargin.trim
    }
}
