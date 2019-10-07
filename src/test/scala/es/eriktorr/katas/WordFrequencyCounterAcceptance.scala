package es.eriktorr.katas

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class WordFrequencyCounterAcceptance extends FlatSpec with Matchers with TableDrivenPropertyChecks {

  private val words = Table(
    ("filename", "wordFrequency"),
    ("test.txt", Map(
      "acquaintance" -> 1,
      "suppose" -> 1,
      "sure" -> 1,
      "know" -> 1)),
    ("input.txt", Map(
      "white" -> 1,
      "tigers" -> 1,
      "live" -> 2,
      "mostly" -> 2,
      "india" -> 1,
      "wild" -> 1,
      "lions" -> 1,
      "africa" -> 1)),
    ("pride-and-prejudice.txt", Map(
      "mr" -> 786,
      "elizabeth" -> 635,
      "very" -> 488,
      "darcy" -> 418,
      "such" -> 395,
      "mrs" -> 343,
      "much" -> 329,
      "more" -> 327,
      "bennet" -> 323,
      "bingley" -> 306,
      "jane" -> 295,
      "miss" -> 283,
      "one" -> 275,
      "know" -> 239,
      "before" -> 229,
      "herself" -> 227,
      "though" -> 226,
      "well" -> 224,
      "never" -> 220,
      "sister" -> 218,
      "soon" -> 216,
      "think" -> 211,
      "now" -> 209,
      "good" -> 201,
      "time" -> 203)))

  "Word frequency counter" should "find the top 25 most used words in a file" in {
    val wordFrequencyCounter = new WordFrequencyCounter

    forAll(words) { (filename, wordFrequency) =>
      wordFrequencyCounter.frequency(filename) should contain only (wordFrequency)
    }
  }

}
