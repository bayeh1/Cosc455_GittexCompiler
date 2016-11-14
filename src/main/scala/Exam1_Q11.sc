import scala.collection.JavaConverters._

/*
a. Develop a function, named convert, that takes a list of integers (you may assume only 0s and 1s) and
returns the list of Boolean equivalents (i.e., 1 for true, 0 for false). For example, if you evaluate
convert(List[1, 1, 0, 1]) it would return [true, true, false, true].
*/

def convert(aList : List[Int]): List[Boolean] = aList.map {
    case 0 => false
    case 1 => true
  }

/*
b. Develop a function, named howManyPassed, that takes a list of Strings (you may assume only “A”,
“B”,  “C”, “D” and “F” are contained in the list) and returns an integer for how many “C” or better
grades are contained in the list. For example, if you evaluate howManyPassed(List[“A”, “A”, “C”,
“D”, “B”, “F”]) it would return 4. Hint, you may use length function, with a syntax of aList.length, to
calculate the length of a list.*/

def howManyPassed (aList : List[String]):Int = {
  aList.filter(_ < "C").length
    //filter(a => (a.filter("A")|| a.equals("B")||a.equals("C"))).length
}
//howManyPassed(List[“A”, “A”, “C”,“D”, “B”,“F”])



/*
c. Develop a function, named cipher, that takes a Char and returns a Char according to the following
rules: ‘a; is replaced by ‘e’; ‘e is replaced by ‘a’; ‘k’ is replaced by ‘c’; ‘c’ is replaced by ‘k’; ‘s’ is
replaced by ‘z’; ‘z’ is replaced by ‘s’. Additionally, develop a function, named jumble, that takes a
String and returns a String with the cipher applied to each character. Hint, to convert a String to a List
of Char, you can use toList and to convert a List of Char to a String, you can use mkString. For
example, if you evaluate jumble(“This class is fun”) would return “Thiz klazz iz fun”.

*/
def cipher(c : Char): Char = c match {
    case 'a' => 'e'
    case 'e' => 'a'
    case 'k' => 'c'
    case 'c' => 'k'
    case 's' => 'z'
    case 'z' => 's'
    case c => c
}
def jumble (a: String): String = {
  (a.toList.map(a => cipher(a))).mkString

}
val s : String = "This class is fun"
jumble(s)




/*
d. Collatz. Develop a function, named collatz, tests the collatz conjecture. That is, your function
should take an integer parameter and return an integer list of the collatz sequence starting with the
integer parameter. A collatz sequence is constructed as follows: given an integer, n, if n is even,
divide it by 2; if n is odd, multiple it by 3 and add 1 (i.e., 3n + 1); repeat this process until you reach
1.
 */
def collatz(n : Int): List[Int] = n match {
  case 1 => 1:: Nil
  case _ =>
    val i = n % 2
     i match {
      case 0 => n :: collatz(n / 2)
      case 1 => n :: collatz((3*n)+1)
    }
  }
collatz(5)

/*
How easy/hard was this exam? Write an Scala function, named cosc455exam1, that takes 1 argument,
an integer. In a Scala worksheet, I should be able to issue the following command cosc455exam1(n);
where n is a non-negative integer. If n is 0, the program will print out “IT WAS EASY!”. If n is greater
than 0, the program will print out “VERY,” n-1 times followed by “HARD!!” for any n. For example, at
the command line, I could issue

cosc455exam1(0)

and your program should output

IT WAS EASY!

If, however, I issue

cosc455exam1(1)

your program should output

HARD!

Finally, if I issue

cosc455exam1(5)

your program should output

VERY, VERY, VERY, VERY HARD!



def collatz(n : Int): List[Int] = n match {
  case 1 => 1::Nil
  case _ =>
    val i = n % 2
     i match {
      case 0 => n :: collatz(n / 2)
      case 1 => n :: collatz((3*n)+1)
    }
  }






 */


def cosc455exam1(n:Int) = n match {
  case 0 => println("IT WAS EASY!")
  case _ =>
    if(n>=1) {
      for( i <- 1  to (n-1)) {
        print("VERY ")
      }
    }
    println("HARD! ")
}

cosc455exam1(5);

