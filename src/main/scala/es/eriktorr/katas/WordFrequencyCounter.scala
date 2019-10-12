package es.eriktorr.katas

import java.io.FileNotFoundException
import java.nio.file.{Files, Paths}

import scala.jdk.StreamConverters._
import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

object WordFrequencyCounter {

  type FileName = String
  type FilePath = String

  type StopWords = List[String]
  type Lines = LazyList[String]
  type Words = LazyList[String]

  type WordFrequency = Map[String, Int]

  private val specialCharactersPattern: Regex = "[\",;.!?(\\-]".r

  val pathTo: FileName => FilePath = (fileName: FileName) => Try(getClass.getClassLoader.getResource(fileName).getPath) match {
    case Success(resourceUri) => resourceUri.asInstanceOf[FilePath]
    case Failure(_) => throw new FileNotFoundException(s"file not found - $fileName")
  }

  val linesFrom: FilePath => Lines = (filePath: FilePath) => {
    val pathToFile = Paths.get(filePath)
    Files.lines(pathToFile).toScala(LazyList)
  }

  val wordsFrom: Lines => Words = {
    _.map(specialCharactersPattern.replaceAllIn(_, " "))
      .flatMap(_.split("\\s+"))
      .map(_.trim.toLowerCase())
      .filter(_.nonEmpty)
  }

  val frequencyOf: (Words, StopWords) => WordFrequency = (words: Words, stopWords: StopWords) => {
    words.filterNot(stopWords.contains(_))
      .map(word => (word, 1))
      .groupMap(_._1)(_._2)
      .map((x: (String, LazyList[Int])) => (x._1, x._2.sum))
      .toSeq
      .sortBy(_._2)
      .takeRight(25)
      .toMap
  }

  def wordFrequencyIn(fileName: FileName, stopWords: StopWords): WordFrequency = {
    val words = pathTo andThen linesFrom andThen wordsFrom apply fileName
    frequencyOf(words, stopWords)
  }

}
