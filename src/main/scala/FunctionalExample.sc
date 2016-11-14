// Functional examples in Scala

List(1,2,3)
List("Brian", "Ayeh")
val someList = List(1,2,3)

var turtles = "I like turtles!" toList

// :: - contructs a list
"hello" :: turtles
println(turtles)

List() == Nil
List[Int] ()

"abc" :: Nil
"abc" :: "def" :: Nil
println("-----------------------------------------------")

val wisc = "Wisconson" toList;

wisc head;
println(wisc)
wisc tail;
wisc isEmpty;
Nil isEmpty;

wisc take 4;
wisc drop 4;
wisc;
wisc splitAt 4;

List(1,2,3).toString()
List(1,2,3).mkString(" is less than ")
List(1,2,3).mkString(" * " )
List(1,2,3).mkString("<:", "--",":>")

val words = List("one", "two", "three")
val numbers = List(1,2,3)

//zip & unzip
val z = words zip numbers
val z2 = numbers zip words
z unzip;


// Higher-Order Functions
// I like turtles

turtles count ((ch: Char) => ch == 't')
turtles count ((josh: Char) => (josh isLetter))
turtles count(_ == 't')
(1 to 24 ) filter (_ < 12)

val numbersList = List(23,6,78,3,3,689,5)
numbersList sortWith(_ < _)
numbersList sortWith(_ > _)

numbersList forall(_ < 1000)
numbersList foreach(n => if (n < 100) println())
numbersList map(i => i*2)

// Write function that takes list
// returns a new list with each element
// incremented by 1

val aList = List(1,5,2,7)
//def myFunc (someList: List) : List = someList map(e => e + 1)
//myFunc(aList)

def exercise1 (aList: List[Int]) : List[Int] = aList.map(element => (element *3) +1)
exercise1(List(1,3,5))

//exercise2

def triplePlus1(n:Int) = (3*n)+1
triplePlus1(5)


def exercise2(bList : List[Int]) : List[Int] = bList.map(triplePlus1)
exercise2(List(4,3,5))

// remove everthing less than 5
// filter and map are high order.

def exercise3(cList : List[Int]) : List[Int] = cList.filter(_>4).map(triplePlus1)

//exercise 4 removes all intergers less than 5

//def exercise4(cList : List[Int]) : List[Int] = cList.filter(_>4).map(triplePlus1).foldLeft(0)(_+_)


//Recursive Solution
// 1.) Base case
// 2.) Recursive step:
//     a.) Contribute to solution and reduce problem
//     b.) recurse with resolved problem
def myMember(element: Int, aList: List[Int]): Boolean = aList match {
  case Nil => false
  case listHead :: listTail => if (element == listHead) true else myMember(element, listTail)
}
myMember(23,List(4,356,2,1))

/*  list2 match {
    case Nil => Nil
    case anotherListHead :: anotherListTail =>
      if (myMember(anotherListHead,aList)) myUnion (aList, anotherListTail)
      else  anotherListHead::myUnion(aList, anothertListTail)
  }*/

//slide 34
def il2fl (aList: List[Int]): List[Float] = aList.map (_.toFloat)

def squarelist(aList: List[Int]): List [Int] = aList.map(e => e*e).filter(_>4)
squarelist(List(1,2,3,4))
val boolList1 : List[Boolean] = List (true, false, false, true)
val boolList2 : List[Boolean] = List (false, false, false, false)
val boolList3 : List[Boolean] = List (true, true, true, true)

def bor (aList : List[Boolean]): Boolean = aList.foldLeft(false)(_||_)

def band (aList : List[Boolean]): Boolean = aList.foldLeft(true)(_&&_)

def evens (aList : List[Int]) = aList.filter( a => a %2 == 0)

def convert (aList : List[Boolean]): List[Int] = aList.map{
  case false => 0
  case true => 1
}




