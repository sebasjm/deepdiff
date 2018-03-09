package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.ListGetter
import diff.patch.accessors.ListSetter
import diff.patch.accessors.RangeListGetter
import diff.patch.accessors.RangeListSetter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class RangeListCoordinates<Type: Any>(
    internal val beforeIndex: Int,
    internal val afterIndex: Int,
    internal val beforeSize: Int,
    internal val afterSize: Int,
    parent: Coordinate<List<Type>, Any>) : RelativeCoordinates<List<Type>, List<Type>>(parent) {

    override fun getter(ofOldState: Boolean, target: List<Type>): Getter<List<Type>> {
        return if (ofOldState)
            RangeListGetter(beforeIndex, beforeSize, target)
        else
            RangeListGetter(afterIndex,afterSize, target)
    }

    override fun setter(ofOldState: Boolean, target: List<Type>): Setter<List<Type>> {
        return if (ofOldState)
            RangeListSetter(beforeIndex, beforeSize, target as MutableList)
        else
            RangeListSetter(afterIndex,afterSize, target as MutableList)
    }

    override val name = parent.name + "[$beforeIndex:${beforeSize}-${afterIndex}:${afterSize}]"

    override fun applies(isOldState: Boolean, target: List<Type>): Boolean {
        return (if (isOldState) beforeIndex else afterIndex) < target.size
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 43 * hash + this.beforeSize
        hash = 43 * hash + this.beforeIndex
        hash = 43 * hash + this.afterIndex
        hash = 43 * hash + this.afterSize
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as RangeListCoordinates<*>?
        if (this.beforeIndex != other!!.beforeIndex) {
            return false
        }
        if (this.beforeSize != other!!.beforeSize) {
            return false
        }
        if (this.afterIndex != other!!.afterIndex) {
            return false
        }
        if (this.afterSize != other!!.afterSize) {
            return false
        }
        return true
    }

}
