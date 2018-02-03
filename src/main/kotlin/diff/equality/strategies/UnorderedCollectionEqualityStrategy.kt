package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.Pair
import diff.equality.PairRelative
import diff.patch.Coordinate
import diff.patch.coords.SetCoordinates

import java.util.*

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class UnorderedCollectionEqualityStrategy<Type: Any> : EqualityStrategy<Set<Type>> {

    override fun compare(before: Set<Type>, after: Set<Type>): EqualityStrategy.CompareResult {
        if (before.isEmpty() && after.isEmpty()) return EqualityStrategy.CompareResult.EQUALS

        val result = after
            .filter { !before.contains(it) }
            .union( before )
            .map {
                { parent: Coordinate<Set<Any>, Any> -> SetCoordinates(it, parent) }
            }

        return EqualityStrategy.UndecidibleMoreToCompare(result)
    }

    override fun shouldUseThisStrategy(cls: Class<Set<Type>>): Boolean {
        return Set::class.java.isAssignableFrom(cls) && !SortedSet::class.java.isAssignableFrom(cls)
    }

}
