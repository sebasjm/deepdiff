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

    override fun getter(target: Parent): Getter<Class<Any>> {
        return ClassGetter(target.javaClass)
    }

    override fun setter(target: Parent): Setter<Class<Any>> {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun relativeName(): String {
        return ":class"
    }

    override fun applies(target: Parent): Boolean {
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