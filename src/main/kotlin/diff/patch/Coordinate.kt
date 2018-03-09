package diff.patch

import diff.patch.coords.RelativeCoordinates
import diff.patch.coords.RootCoordinate

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <Type>
 * *
 * @param <Parent>
</Parent></Type> */
interface Coordinate<Type: Any, Parent: Any> {

    fun getter(ofOldState: Boolean, target: Parent): Getter<Type>
    fun setter(ofOldState: Boolean, target: Parent): Setter<Type>

    val name: String

    fun applies(isOldState: Boolean, target: Parent): Boolean

    fun prev(ofOldState: Boolean, root: Any, result : MutableList<Any?>): Any? {
        return when (this) {
            is RootCoordinate -> root.also{ result.add(it) }
            is RelativeCoordinates -> this.getter(ofOldState, this.parent().prev(ofOldState, root, result) as Parent)
                .get().also { result.add(it) }
            else -> throw RuntimeException("unkown subtye")
        }
    }

    fun asList(ofOldState: Boolean, root: Any): List<Any?> {
        return mutableListOf<Any?>().also { list ->
            this.prev(ofOldState, root, list)
        }
    }

}
