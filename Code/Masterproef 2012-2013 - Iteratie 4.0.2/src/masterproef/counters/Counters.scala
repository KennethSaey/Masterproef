package masterproef.counters

import scala.collection.mutable.HashMap


class Counters extends HashMap[Counter, Int] {

	def +=(counter: Counter, count: Int = 1) = {
		if (contains(counter)) {
			if(this(counter) + count <= 0) {
				remove(counter)
			} else {
				this(counter) = this(counter) + count
			}
		}else {
			this(counter) = count
		}
	}

	def -=(counter: Counter, count: Int = 1) = +=(counter, -count)

	override def -=(counter: Counter): this.type = { +=(counter, -1); this }

	override def toString: String = mkString(" | ")

	/**
	 * Sums up all values of a specific type of a specific counter class
	 *
	 * The <: Counter : Manifest part is needed, aswell as the manifest[A].erasure.isInstance(counter) part.
	 * Has something to do with calling getClass (@see https://issues.scala-lang.org/browse/SI-5042)
	 *
	 * @param A Counter class
	 * @param f value function (Define like: _.valueName)
	 */
	def sum[A <: Counter: Manifest](f: (A => Int)): Int = {
		foldLeft(0) {
			case (result, (counter, times)) => {
				if (manifest[A].erasure.isInstance(counter)) result + times * f(counter.asInstanceOf[A]) else result
			}
		}
	}

	def damage: Int = {
		sum[DamageHealthCounter](_.damage)
	}

	def health: Int = {
		sum[DamageHealthCounter](_.health)
	}

	def poison: Int = {
		sum[PoisonCounter](_.poison)
	}

	def time: Int = {
		sum[TimeCounter](_.time)
	}
}