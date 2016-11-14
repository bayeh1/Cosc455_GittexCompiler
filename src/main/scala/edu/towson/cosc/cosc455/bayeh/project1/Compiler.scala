package edu.towson.cosc.cosc455.bayeh.project1
import java.awt.Desktop
import java.io._
import java.nio.file.Paths
/**
  * Created by niiboye on 10/11/16.
  */
object Compiler {

  var fileContents : String = ""
  var fileName : String = ""
  var currentToken : String = ""
  val Scanner = new MyLexicalAnalyzer
  val Parser = new MySyntaxAnalyzer
  val HTML5 = new MySemanticAnalyzer

  def main(args: Array[String]) {
    //check usage
    checkfile(args)
    readfile(args(0))
    fileName = mkFileName(args(0))


    Scanner.getLexems()


    Scanner.getNextToken()

    Parser.gittex()

    //on return there is a parse tree, Converter
    //Checks for Semantic errors while creating
    //The HTML and then creates the HTML file.

    HTML5.convertToHTML()
  }
  /*Reads the file into a string to be processed.*/
  def readfile(filename : String) ={
    val source = scala.io.Source.fromFile(filename)
    fileContents = try source.mkString finally source.close()

  }
  /*Checks the file for errors:
  * The file must have only 1 argument and be an mkd file */
  def checkfile(args : Array[String]) ={
    if(args.length != 1){
      println("Either no arguments or too many arguments. \nOne argument is required. \nExiting")
      System.exit(1)
    }
    else if(! args(0).endsWith(".mkd")){
      println("The file: " + args(0) + " does not have the correct file extension.\n*.mkd required. \nExiting ")
      System.exit(1)
    }
    /*Creates the file path for the finished HTML document*/
  }
  def mkFileName(fullPath : String): String ={
    fullPath.split('.').init ++ Seq("html") mkString "."
  }


}
