package diff.equality

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
interface DifferenceListener {

    /**
     * called when one of the pair values is null

     * @param pair
     * *
     * @return true if should stop the diff analisis
     */
    fun onOneIsNull(pair: Pair<Any, Any>): Boolean

    /**
     * called when the pair value cant be compared
     * @param pair
     * *
     * @return true if should stop the diff analisis
     */
    fun onNotSameClass(pair: Pair<Any, Any>): Boolean

    /**
     * called when found a difference in the same coordinate

     * @param pair
     * *
     * @return true if should stop the diff analisis
     */
    fun onDifference(pair: Pair<Any, Any>): Boolean

}
