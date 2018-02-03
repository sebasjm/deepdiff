package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.FloatingPointUtil
import diff.equality.Pair

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class FloatingPointEqualityStrategy : EqualityStrategy<Float> {
    internal val epsilon: Double

    constructor() {
        this.epsilon = 1e-6
    }

    constructor(epsilon: Double) {
        this.epsilon = epsilon
    }

    override fun compare(before: Float, after : Float): EqualityStrategy.CompareResult =
        if (before.nearlyEquals(after, epsilon))
            EqualityStrategy.CompareResult.EQUALS
        else
            EqualityStrategy.CompareResult.DIFFERENT

    override  fun shouldUseThisStrategy(cls: Class<Float>) = Float::class.java.isAssignableFrom(cls)

    fun Float.nearlyEquals(other : Float, epsilon: Double ) =
        FloatingPointUtil.nearlyEqual(this.toDouble(), other.toDouble(), epsilon)

}
