package edu.towson.cosc.cosc455.bayeh.project1

/**
  * Created by niiboye on 10/11/16.
  */
object CONSTANTS {
  val DOCB: String = "\\BEGIN"
  val DOCE: String = "\\END"
  val TITLEB : String = "\\TITLE["
  val BRACKETE : String = "]"
  val HEADING : String = "#"
  val PARB : String = "\\PARB"
  val PARE : String = "\\PARE"
  val BOLD : String = "**"
  val ITALICS : String = "*"
  val LISTITEM : String = "+"
  val NEWLINE : String = "\\\\"
  val LINKB : String = "["
  val ADDRESSB : String = "("
  val ADDRESSE : String = ")"
  val IMAGEB : String = "!["
  val DEFB : String = "\\DEF["
  val EQSIGN : String = "="
  val USEB : String = "\\USE["

  //Special char constants


  val specialChar : List[String] = List("\\","[","#","*","!","+")

  val letters : List[String] = List("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
  val num : List[String] = List("1","2","3","4","5","6","7","8","9","0",
    ",",".","\"",":","?","_","/", "'", "")
  val space : List[String] = List(" ", "\t", "\n", "\b","\f","\r")
  val any : List[String] = space ::: letters ::: num


  val REQTEXT : List[String] = List("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
  val TEXT : List[String] = List("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
    "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
    "0","1","2","3","4","5","6","7","8","9",",",".","?","_","/", """ """)



  val beginTag = List(DOCB, TITLEB, HEADING, PARB, LISTITEM, LINKB, IMAGEB, DEFB,
    USEB)
  val endTag= List(DOCE, BRACKETE, PARE, "\n")
  val newline = List(NEWLINE)
  val matchTag = List(BOLD, ITALICS)
  val addressContainer = List(ADDRESSB, ADDRESSE)
  val terminals = beginTag ::: endTag ::: newline ::: matchTag ::: addressContainer ::: specialChar ::: any

  val innerText = List(USEB, HEADING, BOLD, ITALICS, LISTITEM, IMAGEB, LINKB) ::: any






}
