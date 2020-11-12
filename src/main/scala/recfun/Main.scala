package recfun

object Main extends App {
  import FunSets._

  val s1: FunSet = singletonSet(1)
  val s2: FunSet = singletonSet(2)
  val s3: FunSet = singletonSet(3)
  val s: FunSet = union(s1, s2)
  //  forall(union(s, singletonSet(-1000)), _ => true)
  val a: FunSet = map(s, e => e + 20)
  printSet(a)
  //  println(singletonSet(2))
}
