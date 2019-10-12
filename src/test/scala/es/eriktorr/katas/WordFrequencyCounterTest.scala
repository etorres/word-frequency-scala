package es.eriktorr.katas

import java.io.FileNotFoundException

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class WordFrequencyCounterTest extends FlatSpec with Matchers with OneInstancePerTest with MockFactory {

  private val pathToTestResourceFile: String => String = getClass.getClassLoader.getResource(_).getPath

  "Word frequency counter" should "find resource files by their name" in {
    val filePath = pathToTestResourceFile apply "input.txt"
    WordFrequencyCounter.pathTo apply "input.txt" shouldBe filePath
  }

  "Word frequency counter" should "fail with a FileNotFoundException when trying to access a nonexistent file" in {
    the [FileNotFoundException] thrownBy {
      WordFrequencyCounter.pathTo apply "nonexistent.txt"
    } should have message "file not found - nonexistent.txt"
  }

  "Word frequency counter" should "read all lines from text file" in {
    val filePath = pathToTestResourceFile apply "input.txt"
    WordFrequencyCounter.linesFrom apply filePath should contain theSameElementsInOrderAs LazyList (
      "White tigers live mostly in India",
      "Wild lions live mostly in Africa")
  }

}
