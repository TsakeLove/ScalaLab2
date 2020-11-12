package recfun


trait FunSets extends FunSetsInterface {

  override type FunSet = Int => Boolean


  def contains(s: FunSet, elem: Int): Boolean = s(elem)


  def singletonSet(elem: Int): FunSet =
    (n: Int) => n == elem


  def union(s: FunSet, t: FunSet): FunSet =
    (x: Int) => contains(s, x) || contains(t, x)


  def intersect(s: FunSet, t: FunSet): FunSet =
    (x: Int) => contains(s, x) && contains(t, x)


  def diff(s: FunSet, t: FunSet): FunSet =
    (x: Int) => contains(s, x) && !contains(t, x)


  def filter(s: FunSet, p: Int => Boolean): FunSet =
    (x: Int) => contains(s, x) && p(x)



  val bound = 1000


  def forall(s: FunSet, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a == bound) !contains(s, a) || filter(s, p)(a)
      else if (contains(s, a) && !filter(s, p)(a)) false
      else iter(a + 1)
    }
    iter(-bound)
  }


  def exists(s: FunSet, p: Int => Boolean): Boolean = !forall(s, (x: Int) => !p(x))


  def map(s: FunSet, f: Int => Int): FunSet =
    (x: Int) => exists(s, (y: Int) => x == f(y))



  def toString(s: FunSet): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }


  def printSet(s: FunSet): Unit = {
    println(toString(s))
  }
}

object FunSets extends FunSets
