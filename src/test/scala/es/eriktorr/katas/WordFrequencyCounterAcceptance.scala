package es.eriktorr.katas

import es.eriktorr.katas.WordFrequencyCounter.{FileName, FilePath, StopWords}
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

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
    ("the-fall-of-the-house-of-usher.txt", Map(
      "door" -> 12,
      "before" -> 11,
      "certain" -> 12,
      "now" -> 21,
      "long" -> 23,
      "through" -> 15,
      "over" -> 15,
      "character" -> 12,
      "words" -> 10,
      "more" -> 23,
      "one" -> 15,
      "wild" -> 10,
      "portion" -> 11,
      "within" -> 11,
      "well" -> 10,
      "upon" -> 58,
      "usher" -> 25,
      "house" -> 18,
      "even" -> 11,
      "thus" -> 14,
      "shall" -> 9,
      "family" -> 9,
      "many" -> 13,
      "very" -> 12,
      "having" -> 12))
  )

  private val pathToStopWords: FileName => FilePath = {
    getClass.getClassLoader.getResource(_).getPath
  }

  private val readStopWords: String => StopWords = (fileName: String) => {
    val source = Source.fromFile(fileName)
    val stopWords = source.getLines
      .flatMap((line: String) => line.split(","))
      .filter(_.nonEmpty)
      .toList
    source.close()
    stopWords
  }

  private val stopWords = pathToStopWords andThen readStopWords apply "stop_words.txt"

  "Word frequency counter" should "find the top 25 most used words in a file" in {
    forAll(words) { (filename, wordFrequency) =>
      WordFrequencyCounter.wordFrequencyIn(filename, stopWords) shouldBe wordFrequency
    }
  }

}
