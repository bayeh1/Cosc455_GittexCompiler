package edu.towson.cosc.cosc455.bayeh.project1

import java.awt.Desktop
import scala.collection.mutable.Stack
import java.io._
/**
  * Created by bayeh on 11/05/16.
  */
class MySemanticAnalyzer {
  var html : String = ""
  var file = Compiler.fileName
  var stack = Compiler.Parser.stack
  var docStack = Stack[String]()
  var tempStack = Stack[String]()
  var varStack = Stack[String]()
  var boldTag : Int = 0
  var italTag : Int = 0
  var focus : String = ""

  val HTMLOPEN : String = "<HTML>"
  val HTMLCLOSE : String = "</HTML>"
  val HEADOPEN : String = "<HEAD>"
  val HEADCLOSE : String = "</HEAD>"
  val TITLEOPEN : String = "<TITLE>"
  val TITLECLOSE : String = "</TITLE>"
  val BODYOPEN : String = "<BODY>"
  val BODYCLOSE : String = "</BODY>"
  val POPEN : String = "<P>"
  val PCLOSE : String = "</P>"
  val H1OPEN : String = "<H1>"
  val H1CLOSE : String = "</H1>"
  val LIOPEN : String = "<LI>"
  val LICLOSE : String = "</LI>"
  val BOPEN : String = "<B>"
  val BCLOSE : String = "</B>"
  val IOPEN : String ="<I>"
  val ICLOSE : String = "</I>"
  val BR : String = "<BR />"

  def convertToHTML(): Unit ={

    while(!stack.isEmpty){
      focus = stack.pop()
      getHTML()
    }
    html = docStack.mkString
    writeToFile()
    openHTMLFileInBrowser("file.html")
  }
  def getHTML(): Unit ={

    focus.toUpperCase() match{
      case CONSTANTS.DOCB => docStack.push(HTMLOPEN)
      case CONSTANTS.PARB => docStack.push(POPEN)
      case CONSTANTS.PARE => docStack.push(PCLOSE)
      case CONSTANTS.BRACKETE => handleBracTag()
      case CONSTANTS.ADDRESSE => handleImgLink()
      case CONSTANTS.BOLD => handleBold()
      case CONSTANTS.ITALICS => handleItalics()
      case CONSTANTS.NEWLINE => docStack.push(BR)
      case CONSTANTS.DOCE => docStack.push(HTMLCLOSE,BODYCLOSE)
      case "\n" => handleHeadList()
      case _ => docStack.push(focus)
    }

  }
  def handleImgLink(): Unit ={
    while(!stack.top.equals(CONSTANTS.LINKB) && !stack.top.equals(CONSTANTS.IMAGEB)){
      tempStack.push(stack.pop())
    }
    val rootTag = stack.pop()
    if(rootTag.equals(CONSTANTS.LINKB)) handleLink()
    else handleImg()
  }
  def handleLink(): Unit ={

    var link : String = ""
    var linkText : String = ""
    var linkTag : String = ""
    tempStack = tempStack.reverse
    while(!tempStack.top.equals(CONSTANTS.ADDRESSB)){
      link = tempStack.pop() + link
    }
    tempStack.pop()
    while(!tempStack.isEmpty){
      if(!tempStack.top.equals("]")) linkText = tempStack.pop() +linkText
      else tempStack.pop()
    }
    linkTag = "<a href=\"" + link + "\">"+linkText+"</a>"
    docStack.push(linkTag)

  }
  def handleImg(): Unit ={
    var link : String = ""
    var altText : String = ""
    var imgTag : String = ""
    tempStack = tempStack.reverse
    while(!tempStack.top.equals(CONSTANTS.ADDRESSB)){
      link = tempStack.pop() + link
    }
    tempStack.pop()
    while(!tempStack.isEmpty){
      if(!tempStack.top.equals("]")) altText = tempStack.pop() + altText
      else tempStack.pop()
    }
    imgTag = "<img src=\"" + link + "\" alt=\"" + altText +"\">"
    docStack.push(imgTag)
  }
  def handleHeadList(): Unit ={

    while(!stack.top.equals("#") && !stack.top.equals("+")){
      tempStack.push(stack.pop())
    }
    val rootTag : String = stack.pop()
    if(rootTag.equals("#")) handleHead()
    else handleList()

  }
  def handleHead(): Unit ={
    var heading : String = ""
    while(!tempStack.isEmpty) heading += tempStack.pop()
    docStack.push(H1CLOSE,heading,H1OPEN)
  }
  def handleList(): Unit ={
    docStack.push(LICLOSE)
    tempStack = tempStack.reverse

    while(!tempStack.isEmpty){
      val temp : String = tempStack.pop()
      if(temp.equals(CONSTANTS.BOLD)) handleBold()
      else if(temp.equals(CONSTANTS.ITALICS)) handleItalics()
      else if(temp.equals(CONSTANTS.ADDRESSE)){
        var link : String = ""
        var linkText : String = ""
        var linkTag : String = ""

        while(!tempStack.top.equals(CONSTANTS.ADDRESSB)){
          link = tempStack.pop() + link
        }
        tempStack.pop()
        while(!tempStack.top.equals(CONSTANTS.LINKB)){
          if(!tempStack.top.equals("]")) linkText = tempStack.pop() +linkText
          else tempStack.pop()
        }
        tempStack.pop()
        linkTag = "<a href=\"" + link + "\">"+linkText+"</a>"
        docStack.push(linkTag)
      }
      else if(temp.equals(CONSTANTS.BRACKETE)) handleVarUse()
      else docStack.push(temp)
    }
    docStack.push(LIOPEN)
  }
  def handleVarUse(): Unit ={
    val varName = tempStack.pop()
    docStack.push(findVar(varName))
    if(!tempStack.isEmpty) tempStack.pop()

  }
  def handleVarDef(): Unit ={

  }
  def findVar(varName : String): String = {
    var varDef : String = ""
    var found = false
    var inScope = true

    while(!found && !stack.isEmpty){
      if(stack.top.equalsIgnoreCase(CONSTANTS.PARE)) inScope = false
      if(stack.top.equalsIgnoreCase(CONSTANTS.PARB)) inScope = true
      if(stack.top.equalsIgnoreCase(CONSTANTS.DEFB)) {
        if (varStack.top.equals(varName) && inScope){

          stack.push(varStack.pop())
          stack.push(varStack.pop())
          varDef = varStack.top
          found = true
        }
        varStack.push(stack.pop())
      }
      else varStack.push(stack.pop())
    }
    if(varDef.equals("")) SemanticError("Variable "+ varName +" not defined for this scope.")

    while(!varStack.isEmpty){
      stack.push(varStack.pop())
    }
    varDef
  }

  def handleItalics(): Unit ={
    if(italTag == 0){
      docStack.push(ICLOSE)
      italTag = 1
    }
    else{
      docStack.push(IOPEN)
      italTag = 0
    }
  }
  def handleBold(): Unit ={
    if(boldTag == 0){
      docStack.push(BCLOSE)
      boldTag = 1
    }
    else{
      docStack.push(BOPEN)
      boldTag = 0
    }

  }
  def handleBracTag(): Unit ={
    while(stack.top.charAt(0) != '\\'){
      tempStack.push(stack.pop())
    }
    val rootTag : String = stack.pop()
    rootTag.toUpperCase() match{
      case CONSTANTS.TITLEB => handleTitle()
      case CONSTANTS.DEFB => handleVarDef()
      case CONSTANTS.USEB =>handleVarUse()
    }
  }

  def handleTitle(): Unit ={
    var title : String = ""
    while(!tempStack.isEmpty){
      title += tempStack.pop()
    }
    docStack.push(BODYOPEN, TITLECLOSE, title, TITLEOPEN)
  }
  def writeToFile(): Unit ={
    val file = new File("file.html")
    val wr = new BufferedWriter(new FileWriter(file))

    wr.write(html)
    wr.close()
  }
  def openHTMLFileInBrowser(htmlFileStr : String) = {
    val file : File = new File(htmlFileStr.trim)
    println(file.getAbsolutePath)
    if (!file.exists())
      sys.error("File " + htmlFileStr + " does not exist.")

    try {
      Desktop.getDesktop.browse(file.toURI)
    }
    catch {
      case ioe: IOException => sys.error("Failed to open file:  " + htmlFileStr)
      case e: Exception => sys.error("He's dead, Jim!")
    }
  }
  def SemanticError(error : String): Unit ={
    println("Semantic error discovered. \n" + error + "\nExiting.")
    System.exit(1)
  }
}
