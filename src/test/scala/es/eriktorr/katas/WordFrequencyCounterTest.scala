package es.eriktorr.katas

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class WordFrequencyCounterTest extends FlatSpec with Matchers with OneInstancePerTest with MockFactory {

  "Word frequency counter" should "read all lines from text file" in {
    val wordFrequencyCounter = new WordFrequencyCounter

    wordFrequencyCounter.lines("input.txt") should contain theSameElementsInOrderAs LazyList (
      "White tigers live mostly in India",
      "Wild lions live mostly in Africa")
  }

}
