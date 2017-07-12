package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.FloatingPointUtil
import diff.equality.Pair

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class DoubleFloatingPointEqualityStrategy : EqualityStrategy<Double> {
    internal val epsilon: Double

    constructor() {
        this.epsilon = 1e-15
    }

    constructor(epsilon: Double) {
        this.epsilon = epsilon
    }

    override fun compare(before: Double, after: Double) =
        if (before.nearlyEquals(after, epsilon))
            EqualityStrategy.CompareResult.EQUALS
        else
            EqualityStrategy.CompareResult.DIFFERENT

    override fun shouldUseThisStrategy(cls: Class<Double>): Boolean {
        return Double::class.java.isAssignableFrom(cls)
    }

    fun Double.nearlyEquals(other : Double, epsilon: Double ) =
        FloatingPointUtil.nearlyEqual(this, other, epsilon)
}
