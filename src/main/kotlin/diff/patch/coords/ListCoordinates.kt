package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.ListGetter
import diff.patch.accessors.ListSetter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class ListCoordinates<Type: Any>(internal val index: Int, parent: Coordinate<List<Type>, Any>) : RelativeCoordinates<Type, List<Type>>(parent) {

    override fun getter(ofOldState: Boolean, target: List<Type>): Getter<Type> {
        return ListGetter(index, target)
    }

    override fun setter(ofOldState: Boolean, target: List<Type>): Setter<Type> {
        return ListSetter(index, target as MutableList)
    }

    override val name = parent.name + "[$index]"

    override fun applies(isOldState: Boolean, target: List<Type>): Boolean {
        return index < target.size
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 43 * hash + this.index
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as ListCoordinates<*>?
        if (this.index != other!!.index) {
            return false
        }
        return true
    }

}
