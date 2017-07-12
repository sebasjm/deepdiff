package diff.patch.accessors

import diff.patch.Getter

import java.lang.reflect.Array

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class ArrayGetter<ArrayType: Any, ComponentType: Any>(internal val target: ArrayType, internal val index: Int) : Getter<ComponentType> {

    override fun get(): ComponentType? {
        return if (index < Array.getLength(target)) Array.get(target, index) as ComponentType else null
    }

}
