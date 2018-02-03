package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.Pair
import diff.equality.PairRelative
import diff.patch.Coordinate
import diff.patch.coords.ListCoordinates

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class OrderedCollectionEqualityStrategy : EqualityStrategy<List<Any>> {

    override fun compare(before : List<Any>, after : List<Any>): EqualityStrategy.CompareResult {
        if (before.isEmpty() && after.isEmpty()) return EqualityStrategy.CompareResult.EQUALS

        val result = (0.. Math.max(before.size, after.size) - 1).map {
            { parent: Coordinate<List<Any>, Any> -> ListCoordinates(it, parent) }
        }

        return EqualityStrategy.UndecidibleMoreToCompare(result)
    }

    override fun shouldUseThisStrategy(cls: Class<List<Any>>): Boolean {
        return List::class.java.isAssignableFrom(cls)
    }

}
