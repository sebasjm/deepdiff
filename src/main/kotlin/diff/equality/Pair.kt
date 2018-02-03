package diff.equality

import diff.patch.Coordinate

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 * *
 * @param <Type>
</Type> */
interface Pair<Type: Any, Parent: Any> {

    /**

     * @return true if before == after
     */
    fun sameObject(): Boolean

    /**

     * @return true if before.class == after.class, implies bothNotNull if !sameObject
     */
    fun sameClass(): Boolean

    /**

     * @return true if before != null && after != null
     */
    fun bothNotNull(): Boolean

    /**

     * @return path to the current values, returns null for the root pair
     */
    fun coordinate(): Coordinate<Type, Parent>

    /**
     * the before value
     * @return
     */
    fun before(): Type?

    /**
     * the after value
     * @return
     */
    fun after(): Type?

    /**

     * @return null if both are null, after.class if before is null, otherwise before.class
     */
    fun type(): Class<Type>?

    interface SameTypePair<Type: Any> {

        fun coordinate(): Coordinate<Type, Any>

        fun before(): Type

        fun after(): Type

        fun type(): Class<Type>

    }
}
