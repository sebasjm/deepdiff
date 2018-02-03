package diff.patch.accessors

import diff.patch.Setter

import java.lang.reflect.Field

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class FieldSetter<Type: Any>(internal val target: Any, internal val field: Field) : Setter<Type> {

    init {
        field.isAccessible = true
    }

    override fun set(value: Type) {
        try {
            field.set(target, value)
        } catch (ignored: IllegalArgumentException) {
            ignored.printStackTrace()
        } catch (ignored: IllegalAccessException) {
            ignored.printStackTrace()
        }

    }

}
