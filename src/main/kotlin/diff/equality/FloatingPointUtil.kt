package diff.equality

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
object FloatingPointUtil {

    fun nearlyEqual(first: Double, second: Double, epsilon: Double): Boolean {
        val diff = Math.abs(first - second)

        return (first == second) || if (first == 0.0 || second == 0.0 || diff < java.lang.Double.MIN_NORMAL)
            diff < epsilon * java.lang.Double.MIN_NORMAL
        else
            diff / (Math.abs(first) + Math.abs(second)) < epsilon
    }

}
