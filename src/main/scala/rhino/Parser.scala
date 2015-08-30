package rhino

import org.mozilla.javascript.ast.{FunctionCall, ArrayLiteral, AstNode, NodeVisitor}
import org.mozilla.javascript.{Node, IRFactory, CompilerEnvirons}
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

object parser {
  val compileEnvirons = new CompilerEnvirons()
  compileEnvirons.setRecoverFromErrors(true)
  compileEnvirons.setGenerateDebugInfo(true)
  compileEnvirons.setRecordingComments(true)

  // The IRFactory rewrites the parse tree into an IR suitable for codegen.
  val irFactory = new IRFactory(compileEnvirons)

  def parse(sourceCode:String) = irFactory.parse(sourceCode, null, 0)
}


class DebugVisitor extends NodeVisitor {
  override def visit(node: AstNode): Boolean = {
    val indent = " " * node.depth()
    println(f"${node.getAbsolutePosition}%04d-${node.getLength}%03d|" +
      f"${indent}${node.getClass.getSimpleName}")
    true
  }
}

class NodeToListVisitor extends NodeVisitor {
  val nodes:ListBuffer[AstNode] = ListBuffer()

  override def visit(node: AstNode): Boolean = {
    nodes.append(node)
    true
  }

  def getNodes:List[AstNode] = nodes.sortBy(
    node => node.getAbsolutePosition
  ).toList
}