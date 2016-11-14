package edu.towson.cosc.cosc455.bayeh.project1

/**
  * Created by niiboye on 10/11/16.
  */
trait LexicalAnalyzer {

  def addChar() : Unit
  def getChar() : Char
  //def getNextChar():Char
  def getNextToken() : Unit
  def lookup() : Boolean


}
