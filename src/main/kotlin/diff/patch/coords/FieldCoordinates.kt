package diff.patch.coords

import diff.getDeepDeclaredFields
import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.FieldGetter
import diff.patch.accessors.FieldSetter

import java.lang.reflect.Field
import java.util.Objects

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class FieldCoordinates<Type: Any, Parent: Any>(internal val field: String, parent: Coordinate<Parent, Any>) : RelativeCoordinates<Type, Parent>(parent) {

    override fun getter(ofOldState: Boolean, target: Parent): Getter<Type> {
        try {
            return FieldGetter(target, searchFieldThroughHierarchy(this.field, target.javaClass))
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return Getter.nullObject as Getter<Type>

    }

    //TODO: fix later
    @Throws(SecurityException::class, NoSuchFieldException::class)
    private fun searchFieldThroughHierarchy(field: String, clazz: Class<*>?): Field {
        if (clazz == null) {
            throw NoSuchFieldException(field)
        }
        return try {
            clazz.getDeclaredField(field)
        } catch (ex: NoSuchFieldException) {
            searchFieldThroughHierarchy(field, clazz.superclass)
        }

    }

    override fun setter(ofOldState: Boolean, target: Parent): Setter<Type> {
        try {
            return FieldSetter(target, searchFieldThroughHierarchy(this.field, target.javaClass))
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return Setter.nullObject as Setter<Type>

    }

    override val name = parent.name + ".$field"

    override fun applies(isOldState: Boolean, target: Parent): Boolean {
        return target.javaClass.getDeepDeclaredFields().contains(this.field)
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 13 * hash + Objects.hashCode(this.field)
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as FieldCoordinates<*, *>?
        if (this.field != other!!.field) {
            return false
        }
        return true
    }

}
