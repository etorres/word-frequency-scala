package es.eriktorr.katas

import java.io.FileNotFoundException
import java.nio.file.{Files, Paths}

import scala.jdk.StreamConverters._
import scala.util.{Failure, Success, Try}

object WordFrequencyCounter {

  type FileName = String
  type WordFrequency = Map[String, Int]
  type FilePath = String
  type FileLines = LazyList[String]

  val pathTo: FileName => FilePath = (fileName: FileName) => Try(getClass.getClassLoader.getResource(fileName).getPath) match {
    case Success(resourceUri) => resourceUri.asInstanceOf[FilePath]
    case Failure(_) => throw new FileNotFoundException(s"file not found - $fileName")
  }

  val linesFrom: FilePath => FileLines = (filePath: FilePath) => {
    val pathToFile = Paths.get(filePath)
    Files.lines(pathToFile).toScala(LazyList)
  }

  val countWordsIn: FileLines => WordFrequency = (fileLines: FileLines) => {
    fileLines.map(_.split("\\s+"))
      .filter(_.nonEmpty)
      .flatten
      .map(word => (word.toLowerCase, 1))
      .groupMap(_._1)(_._2)
      .map((x: (String, LazyList[Int])) => (x._1, x._2.sum))
  }

  def wordFrequencyIn(fileName: FileName): WordFrequency = {
    pathTo andThen linesFrom andThen countWordsIn apply fileName
  }

}
