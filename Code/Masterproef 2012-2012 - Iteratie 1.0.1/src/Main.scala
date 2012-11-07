import scala.io.Source

import masterproef.game.Gameflow

object Main {

	def main(args: Array[String]): Unit = {
		println(Gameflow.parse(Source.fromFile("src/masterproef/data/test.txt").mkString))
	}

}