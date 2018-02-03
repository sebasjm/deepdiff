package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.patch.Coordinate
import diff.patch.coords.ListCoordinates
import diff.patch.coords.MapCoordinates
import diff.patch.coords.RelativeCoordinates
import java.util.*

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class OrderedMapEqualityStrategy : EqualityStrategy<SortedMap<Any, Any>> {

    override fun compare(before: SortedMap<Any, Any>, after: SortedMap<Any, Any>): EqualityStrategy.CompareResult {
        if (before.isEmpty() && after.isEmpty()) return EqualityStrategy.CompareResult.EQUALS

        val keysResult = after.keys
            .filter { !before.keys.contains(it) }
            .union( before.keys )
            .map {
                { parent: Coordinate<Any, Any> -> MapCoordinates(it, parent as Coordinate<Map<Any, Any>, Any>) as RelativeCoordinates<Any, Any> }
            }

        val orderResult = (0.. Math.max(before.size, after.size) - 1)
            .map {
                { parent: Coordinate<Any, Any> -> ListCoordinates(it, parent as Coordinate<List<Any>, Any>) as RelativeCoordinates<Any, Any> }
            }

        return EqualityStrategy.UndecidibleMoreToCompare(keysResult.union(orderResult).toList())
    }

    override fun shouldUseThisStrategy(cls: Class<SortedMap<Any, Any>>): Boolean {
        return SortedMap::class.java.isAssignableFrom(cls)
    }

}

