package edu.towson.cosc.cosc455.bayeh.project1

import sun.jvm.hotspot.memory.Space
import scala.collection.mutable.ArrayBuffer

/**
  * Created by niiboye on 10/11/16.
  */
class MyLexicalAnalyzer extends LexicalAnalyzer{

  var position : Int = 0
  var nextChar : Char = 0
  var currentToken : String = ""
  var lexems : ArrayBuffer[String] = new ArrayBuffer[String]()
  var itr : Iterator[String] = null
  val terminals = CONSTANTS.terminals

  val begSlash : Char = '\\'
  val leftB : Char = '['
  val EX : Char = '!'
  val head : Char = '#'
  val list : Char = '+'
  val boldItal : Char = '*'

  override def getNextToken(): Unit = {
    Compiler.currentToken = itr.next()
  }
  /*Returns true if more tokens exist*/
  def hasNextToken(): Boolean = {
    itr.hasNext
  }

  def getLexems(): Unit = {
    while(position < Compiler.fileContents.length()){
      nextChar = getChar()
      nextToken()
    }
    itr = lexems.iterator
  }

  def nextToken(): Unit ={
    if(special()){
      addChar()
      specialToken(nextChar)
    }
    else if(text()){
      addText()
    }
    else if(nextChar.toString == " ") {}
    else{
      invalidTokenError(nextChar+"3")
    }
  }
  def specialToken(c : Char): Unit = {
    c match{
      case `begSlash` => slash()
      case `leftB` => leftBrack()
      case EX => exclaim()
      case `head` => headingList()
      case `list` => headingList()
      case `boldItal` => sTags()
    }
  }

  def slash(): Unit ={
    nextChar = getChar()
    if(nextChar == begSlash){
      addChar()
      addToken()
    }
    else{

      while(nextChar != '\n' && nextChar != '['){
        if(letters()) addChar()
        else if(space()) {}//Do Nothing
        else invalidTokenError("'"+nextChar+"' 1");
        nextChar = getChar()
      }
      if(nextChar == '[') addChar()

      if(!lookup()) invalidTokenError(" "+currentToken+" 2")
      addToken()

      if(lexems.last.equalsIgnoreCase(CONSTANTS.TITLEB) || lexems.last.equalsIgnoreCase(CONSTANTS.USEB) ||
        lexems.last.equalsIgnoreCase(CONSTANTS.DEFB)){
        nextChar = getChar()
        while(nextChar != ']'){
          addInnerText()
          nextChar = getChar()
        }
        if(nextChar == ']') addText()
      }
    }

  }

  def leftBrack(): Unit ={
    addText()
    nextChar = getChar()
    addLink()
  }

  def exclaim(): Unit ={
    nextChar = getChar()
    if(nextChar != '[') invalidTokenError(nextChar+"")
    addChar()
    addToken()
    nextChar = getChar()
    addLink()
  }
  def headingList(): Unit ={
    addText()
    nextChar = getChar()
    while(nextChar != '\n'){
      nextToken()
      nextChar = getChar()
    }
    addLine()
  }

  def sTags(): Unit ={
    currentToken = nextChar+""
    nextChar = getChar()
    if(nextChar == '*'){
      currentToken += '*'
      addToken()
    }
    else{
      addToken()
      position = position-1

    }
  }


  override def getChar(): Char = {
    val c: Char = Compiler.fileContents.charAt(position)
    position += 1
    c
  }

  override def addChar(): Unit = {
    currentToken += nextChar
  }

  def addToken(): Unit ={
    lexems += currentToken
    newToken()
  }

  def addText(): Unit ={
    if(nextChar != '\n' && nextChar != '\t'){
      newToken()
      addChar()
      addToken()
    }
  }

  def addLine(): Unit ={
    if(nextChar == '\n'){
      newToken()
      addChar()
      addToken()
    }
  }

  def addInnerText(): Unit ={
    if(text() || CONSTANTS.EQSIGN.equals("=")){
      addText()
    }
  }

  def addLink(): Unit ={
    while(nextChar != ']'){
      addText()
      nextChar = getChar()
    }
    if(nextChar == ']') addText()
    nextChar = getChar()
    delete()
    if(nextChar == '(') addText()
    nextChar = getChar()
    while(nextChar != ')'){
      addText()
      nextChar = getChar()
    }

    addText()
  }


  def delete(): Unit ={
    while(space()){
      nextChar = getChar()
    }
  }

  def newToken(): Unit ={
    currentToken = ""
  }


  def text(): Boolean = {
    CONSTANTS.any.exists(x => x.equalsIgnoreCase(nextChar+""))
  }

  def letters(): Boolean = {
    CONSTANTS.letters.exists(x => x.equalsIgnoreCase(nextChar+""))
  }
  def special(): Boolean = {
    CONSTANTS.specialChar.exists(x => x.equals(nextChar+""))
  }
  def space(): Boolean = {
    CONSTANTS.space.exists(x => x.equals(nextChar+""))
  }
  override def lookup(): Boolean = {
    terminals.exists(x => currentToken.equalsIgnoreCase(x))
  }

  def invalidTokenError(token:String) = {
    println("Lexical error! " +token + " is not a valid symbol.")
    System.exit(1)
  }
}
