package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.Pair
import diff.equality.PairRelative
import diff.patch.Coordinate
import diff.patch.coords.MapCoordinates

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class UnorderedMapEqualityStrategy : EqualityStrategy<Map<Any, Any>> {

    override fun compare(before : Map<Any, Any>, after : Map<Any, Any>): EqualityStrategy.CompareResult {
        if (before.isEmpty() && after.isEmpty()) return EqualityStrategy.CompareResult.EQUALS

        val result = after.keys
            .filter { !before.keys.contains(it) }
            .union( before.keys )
            .map {
                { parent: Coordinate<Map<Any, Any>, Any> -> MapCoordinates(it, parent) }
            }

        return EqualityStrategy.UndecidibleMoreToCompare(result)
    }

    override fun shouldUseThisStrategy(cls: Class<Map<Any, Any>>): Boolean {
        return Map::class.java.isAssignableFrom(cls)
    }

}
