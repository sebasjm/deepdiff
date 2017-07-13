package diff.patch.accessors

import diff.patch.Getter

import java.lang.reflect.Field

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class FieldGetter<Type: Any>(internal val target: Any, internal val field: Field) : Getter<Type> {

    init {
        field.isAccessible = true
    }

    override fun get(): Type? {
        try {
            return field.get(target) as Type
        } catch (ignored: IllegalArgumentException) {
            ignored.printStackTrace()
            return null
        } catch (ignored: IllegalAccessException) {
            ignored.printStackTrace()
            return null
        }

    }

}
