package edu.towson.cosc.cosc455.bayeh.project1
import scala.collection.mutable.Stack
import edu.towson.cosc.cosc455.bayeh.project1

/**
  * Created by niiboye on 10/11/16.
  */
class MySyntaxAnalyzer() extends SyntaxAnalyzer{


  var stack  = Stack[String]()
  val scanner = Compiler.Scanner

  override def gittex(): Unit = {
    if(project1.Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCB)){
      parseTree()
      varCheck()
      title()
      body()
      if(project1.Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCE)){
        parseTree()
      }
      else SyntaxError("Expect end tag in place of " + Compiler.currentToken)
      if(scanner.hasNextToken()) SyntaxError("Tokens found after End tag")
    }
  }
  def varCheck(): Unit ={
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DEFB)){
      variableDefine()
      varCheck()
    }

  }
  override def title(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.TITLEB)){
      parseTree()
      reqText()
      if(Compiler.currentToken.equals(CONSTANTS.BRACKETE)){
        parseTree()
      }
      else SyntaxError("Expect closing title tag in place of " + Compiler.currentToken)
    }
    else SyntaxError("Expected " + CONSTANTS.TITLEB +" in place of " + Compiler.currentToken)
  }

  override def body(): Unit = {
    if(CONSTANTS.innerText.exists(x => x.equalsIgnoreCase(Compiler.currentToken))){
      innerText()
      body()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.PARB)){
      paragraph()
      body()
    }
    if(Compiler.currentToken.equals(CONSTANTS.NEWLINE)){
      newline()
      body()
    }
  }

  override def paragraph(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.PARB)){
      parseTree()
      variableDefine()
      innerText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.PARE)){
        parseTree()
      }
      else SyntaxError("Expected Paragraph Closing tag in place of " + Compiler.currentToken)
    }
  }

  override def innerText(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      variableUse()
      innerText()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.HEADING)){
      heading()
      innerText()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      bold()
      innerText()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      italics()
      innerText()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM)){
      listItem()
      innerText()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB)){
      image()
      innerText()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      link()
      innerText()
    }
    if(validText()){
      parseTree()
      innerText()
    }
  }

  override def heading(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.HEADING)){
      parseTree()
      reqText()
    }
  }

  override def variableDefine(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DEFB)){
      parseTree()
      addVar()
      if(Compiler.currentToken.equals(CONSTANTS.EQSIGN)){
        parseTree()
      }
      else SyntaxError("Syntax error ")
      addVar()
      if(Compiler.currentToken.equals(CONSTANTS.BRACKETE)){
        parseTree()
      }
      else SyntaxError("Expected closing variable definition tag in place of ")
    }
  }

  override def variableUse(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      parseTree()
      addVar()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree()
      }
      else SyntaxError("Missing closing variable use tag!  ")
    }
  }

  override def bold(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      parseTree()
      getText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
        parseTree()
      }
      else SyntaxError("Missing closing bold tag! " )
    }
  }

  override def italics(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      parseTree()
      getText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
        parseTree()
      }
      else SyntaxError("Missing closing italics tag! " + Compiler.currentToken)
    }
  }

  override def listItem(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM)){
      parseTree()
      innerItem()
      listItem()
    }
  }

  override def innerItem(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      variableUse()
      innerItem()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      bold()
      innerItem()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      italics()
      innerItem()
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      link()
      innerItem()
    }
    if(validText()){
      reqText()
    }
  }

  override def link(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      parseTree()
      reqText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree()
        if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB)){
          parseTree()
          reqText()
          if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE)){
            parseTree
          }
          else SyntaxError("Missing closing link tag! " )
        }
        else SyntaxError("Missing opening address tag! " )
      }
      else SyntaxError("Missing right bracket tag! " )
    }
    else SyntaxError("Missing opening link tag! " )
  }

  override def image(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB)){
      parseTree()
      reqText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree()
        if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB)){
          parseTree()
          reqText()
          if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE)){
            parseTree
          }
          else SyntaxError("Missing closing tag! " )
        }
        else SyntaxError("Missing opening address tag! " )
      }
      else SyntaxError("Missing right bracket tag!  " )
    }
    else SyntaxError("Missing opening image tag!  " +)
  }

  override def newline(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)){
      parseTree()
    }
  }

  def parseTree(): Unit ={
    stack.push(Compiler.currentToken)

    if(scanner.hasNextToken()){
      scanner.getNextToken()
    }
  }
  def reqText(): Unit ={
    if(validText()){
      getText()
    }
    else SyntaxError("Text ")
  }
  def getText(): Unit ={
    while(validText()){
      parseTree()
    }
  }
  def addVar(): Unit ={
    var token : String = ""
    while(validText()){

      if(!CONSTANTS.space.exists(x => x.equals(Compiler.currentToken))){
        token += Compiler.currentToken
      }

      scanner.getNextToken()
    }
    stack.push(token)
  }
  def validText(): Boolean ={
    CONSTANTS.any.exists(x => x.equalsIgnoreCase(Compiler.currentToken))
  }

  //Errors
  def SyntaxError(error : String): Unit ={
    println("Syntax error! " + error + "\n Exiting.")
    System.exit(1)
  }
  }