package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.ClassGetter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <Parent>
</Parent> */
class ClassCoordinates<Parent: Any>(parent: Coordinate<Parent, Any>) : RelativeCoordinates<Class<Any>, Parent>(parent) {

    override fun getter(ofOldState: Boolean, target: Parent): Getter<Class<Any>> {
        return ClassGetter(target.javaClass)
    }

    override fun setter(ofOldState: Boolean, target: Parent): Setter<Class<Any>> {
        throw UnsupportedOperationException("Not supported.")
    }

    override val name = parent.name +":class"

    override fun applies(isOldState: Boolean, target: Parent): Boolean {
        return true
    }

    override fun hashCode(): Int {
        val hash = 41
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        return true
    }
}
