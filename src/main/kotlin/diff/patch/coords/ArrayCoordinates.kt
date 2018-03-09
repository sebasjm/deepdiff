package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.ArrayGetter
import diff.patch.accessors.ArraySetter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <ComponentType>
 * *
 * @param <ArrayType>
</ArrayType></ComponentType> */
class ArrayCoordinates<ComponentType: Any, ArrayType: Any>(internal val index: Int, parent: Coordinate<ArrayType, Any>) : RelativeCoordinates<ComponentType, ArrayType>(parent) {

    override fun getter(ofOldState: Boolean, target: ArrayType): Getter<ComponentType> {
        return ArrayGetter(target, index)
    }

    override fun setter(ofOldState: Boolean, target: ArrayType): Setter<ComponentType> {
        return ArraySetter(target, index)
    }

    override val name = parent.name + "[$index]"

    override fun applies(isOldState: Boolean, target: ArrayType): Boolean {
        return target.javaClass.isArray
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = 41 * hash + this.index
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as ArrayCoordinates<*, *>?
        if (this.index != other!!.index) {
            return false
        }
        return true
    }

}
