package diff.patch.accessors

import diff.patch.Getter
import diff.patch.Setter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class SimpleGetterSetter<Type: Any>(internal var obj: Type?) : Getter<Type>, Setter<Type> {

    override fun get(): Type? {
        return obj
    }

    override fun set(value: Type) {
        obj = value
    }

}
