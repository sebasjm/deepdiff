package diff.patch

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 * *
 * @param <Type>
</Type> */
class Patch<Type>(
        val coordinate: Coordinate<*, *> , //where changed
        val type: Class<*>, //what changed
        val delta: Delta<*> //how changed
    ) {

    fun apply(`object`: Type): Patch<Type> {
        //        delta.apply(coordinate.getter(object), coordinate.setter(object));
        return this
    }

    override fun toString(): String {
        return "diff{what=$coordinate,how=$delta}"
    }

}
