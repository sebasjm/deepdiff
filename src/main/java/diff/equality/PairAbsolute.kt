package diff.equality

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.coords.RootCoordinate

import java.util.Objects

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class PairAbsolute<Type: Any>(private val before: Type?, private val after: Type?) : Pair<Type, Any> {
    private val beforeGetter: Getter<Type>
    private val afterGetter: Getter<Type>
    private val type: Class<Type>?
    private val coordinate = RootCoordinate<Type>()

    init {
        this.beforeGetter = if (before == null) Getter.nullObject as Getter<Type> else this.coordinate.getter(before)
        this.afterGetter  = if (after  == null) Getter.nullObject as Getter<Type> else this.coordinate.getter(after)
        this.type = afterGetter.get()?.javaClass ?: beforeGetter.get()?.javaClass
    }

    override fun sameObject(): Boolean {
        return before === after
    }

    override fun sameClass(): Boolean {
        return sameObject() || bothNotNull() && before?.javaClass == after?.javaClass
    }

    override fun bothNotNull(): Boolean {
        return before != null && after != null
    }

    override fun coordinate(): Coordinate<Type, Any> {
        return coordinate
    }

    override fun before(): Type? {
        return before
    }

    override fun after(): Type? {
        return after
    }

    override fun type(): Class<Type>? {
        return type
    }

    override fun toString(): String {
        return "abs_pair(before:$before,after:$after)"
    }

    override fun hashCode(): Int {
        var hash = 3
        hash = 97 * hash + Objects.hashCode(this.before)
        hash = 97 * hash + Objects.hashCode(this.after)
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as PairAbsolute<*>?
        if (this.before != other!!.before) {
            return false
        }
        if (this.after != other.after) {
            return false
        }
        return true
    }

}
