package diff.patch.accessors

import diff.patch.Setter

import java.lang.reflect.Array

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class ArraySetter<ArrayType: Any, ComponentType: Any>(internal val target: ArrayType, internal val index: Int) : Setter<ComponentType> {

    override fun set(value: ComponentType) {
        if (index < Array.getLength(target)) {
            Array.set(target, index, value)
        }
    }

}
