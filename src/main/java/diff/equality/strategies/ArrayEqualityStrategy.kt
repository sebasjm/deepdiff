package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.EqualityStrategy.UndecidibleMoreToCompare
import diff.patch.Coordinate
import diff.patch.coords.ArrayCoordinates

import java.lang.reflect.Array

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class ArrayEqualityStrategy<ArrayType: Any> : EqualityStrategy<ArrayType> {

    /**
     * Arrays should have the same size

     * @param pair
     * *
     * @return
     */
    override fun compare(before: ArrayType, after: ArrayType): EqualityStrategy.CompareResult {
        val lengthBefore = Array.getLength(before)
        val lengthAfter = Array.getLength(after)
        if (lengthBefore == 0 && lengthAfter == 0) return EqualityStrategy.CompareResult.EQUALS

        val max = Math.max(lengthBefore, lengthAfter) - 1

        val result = (0..max).map {
            { parent: Coordinate<Any, Any> -> ArrayCoordinates<Any, Any>(it, parent) }
        }

        return UndecidibleMoreToCompare(result)
    }

    override fun shouldUseThisStrategy(clazz: Class<ArrayType>) = clazz.isArray

}
