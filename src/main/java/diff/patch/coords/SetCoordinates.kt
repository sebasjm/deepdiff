package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.SetGetter
import diff.patch.accessors.SetSetter

import java.util.Objects

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class SetCoordinates<Type: Any>(internal val element: Type, parent: Coordinate<Set<Type>, Any>) : RelativeCoordinates<Type, Set<Type>>(parent) {

    override fun getter(target: Set<Type>): Getter<Type> {
        return SetGetter(target, element)
    }

    override fun setter(target: Set<Type>): Setter<Type> {
        return SetSetter(target, element)
    }

    override fun relativeName(): String {
        return "('$element')"
    }

    override fun applies(target: Set<Type>): Boolean {
        return target.contains(element)
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 61 * hash + Objects.hashCode(this.element)
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as SetCoordinates<*>?
        if (this.element != other!!.element) {
            return false
        }
        return true
    }

}
