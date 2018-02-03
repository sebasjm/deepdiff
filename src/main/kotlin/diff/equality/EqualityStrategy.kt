package diff.equality

import diff.patch.Coordinate
import diff.patch.coords.RelativeCoordinates

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 * *
 * @param <Type>
</Type> */
interface EqualityStrategy<Type: Any> {

    /**

     * @param <Type>
    </Type> */
    interface CompareResult {
        companion object {
            val EQUALS: CompareResult = object : CompareResult {}
            val DIFFERENT: CompareResult = object : CompareResult {}
        }
    }

    class UndecidibleMoreToCompare<Type: Any>(val result: List<(parent: Coordinate<Type, Any>) -> RelativeCoordinates<Any, Type>>) : CompareResult

    fun shouldUseThisStrategy(cls: Class<Type>): Boolean

    /**

     * @param pair
     * *
     * @return true if the current stack contains a pair that is equal
     */
    fun compare(before: Type, after: Type): CompareResult

}
