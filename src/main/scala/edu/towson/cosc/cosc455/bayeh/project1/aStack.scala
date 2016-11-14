package edu.towson.cosc.cosc455.bayeh.project1
import java.util.ArrayList
import java.util.EmptyStackException
//remove if not needed

/**
  * Created by bayeh on 11/11/16.
  */
class aStack[S] {
   var arrayList: ArrayList[S] = new ArrayList[S]()

  def push(item: S) {
    arrayList.add(item)
  }

  def pop(): S = {
    if (!isEmpty) arrayList.remove(size - 1) else throw new EmptyStackException()
  }

  def isEmpty(): Boolean = (arrayList.size == 0)

  def peek(): S = {
    if (!isEmpty) arrayList.get(size - 1) else throw new EmptyStackException()
  }

  def size(): Int = arrayList.size

}
