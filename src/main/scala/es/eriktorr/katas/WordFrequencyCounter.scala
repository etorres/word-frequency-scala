package es.eriktorr.katas

import java.io.FileNotFoundException
import java.nio.file.{Files, Paths}

import scala.jdk.StreamConverters._
import scala.util.{Failure, Success, Try}

class WordFrequencyCounter {

  def frequency(filename: String): Map[String, Int] = ???

  def lines(filename: String): LazyList[String] = {
    val resourceUrl = getClass.getClassLoader.getResource(filename)
    val resourceUri = Try(resourceUrl.getPath) match {
      case Success(resourceUri) => resourceUri
      case Failure(_) => throw new FileNotFoundException(s"file not found - ${filename}")
    }
    Files.lines(Paths.get(resourceUri)).toScala(LazyList)
  }

}
