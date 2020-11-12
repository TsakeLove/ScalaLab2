package recfun

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1: FunSet = singletonSet(1)
    val s2: FunSet = singletonSet(2)
    val s3: FunSet = singletonSet(3)
  }

  @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 3), "Singleton not contains other value them provided")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s: FunSet = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersect must return subset of both sets`: Unit = {
    new TestSets {
      val s12: FunSet = union(s1, s2)
      val s23: FunSet = union(s2, s3)
      assert(contains(intersect(s12, s23), 2), "1,2 ^ 2,3 == 2")
    }
  }

  @Test def `diff of sets`: Unit = {
    new TestSets {
      val s12: FunSet = union(s1, s2)
      val s23: FunSet = union(s2, s3)
      val difference: FunSet = diff(s12, s23)
      assert(contains(difference, 1), "1,2 - 2,3 contains 1")
      assert(!contains(difference, 3), "1,2 - 2,3 should not contain 3")
      assert(!contains(difference, 2), "1,2 - 2,3 should not contain 2")
    }
  }

  @Test def `filter of sets`: Unit = {
    new TestSets {
      val s: FunSet = union(union(s1, s2), s3)
      val filtered:FunSet = filter(s, e => e % 2 == 0)
      assert(contains(filtered, 2), "1, 2, 3 filter even contains 2")
      assert(!contains(filtered, 3), "1,2, 3 filtered even should not contain 3")
    }
  }

  @Test def `forall on boundary values`: Unit = {
    new TestSets {
      val s: FunSet = union(s1, s2)
      assert(forall(union(s, singletonSet(-1000)), _ => true), "-1000")
      assert(forall(union(s, singletonSet(1000)), _ => true), "1000")
    }
  }

  @Test def `forall on predicate`: Unit = {
    new TestSets {
      val s: FunSet = union(s1, s2)
      assert(forall(union(s, singletonSet(-1000)), e => e > -1001), "-1000")
      assert(!forall(union(s, singletonSet(1000)), e => e == 1), "1000")
    }
  }


  @Test def `exists on predicate`: Unit = {
    new TestSets {
      val s: FunSet = union(s1, s2)
      assert(exists(s, e => e > 1), "1, 2 --> 2 > 1")
      assert(!exists(s, _ > 3), "1, 2 --> all less then 3")
    }
  }

  @Test def `map contains result`: Unit = {
    new TestSets {
      val s: FunSet = union(s1, s2)
      assert(contains(map(s, e => e + 5), 6), "1, 2 --> 6, 7")
    }
  }


  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
