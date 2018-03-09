package diff.equality

import diff.patch.Coordinate
import diff.patch.Getter

import java.util.Objects

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <Type>
 * *
 * @param <Parent>
</Parent></Type> */
class PairRelative<Type: Any, Parent: Any>(val parent: Pair<Parent, Any>, private val coordinate: Coordinate<Type, Parent>, before: Parent, after: Parent) : Pair<Type, Parent> {
    private val beforeGetter: Getter<Type>
    private val afterGetter: Getter<Type>
    private val type: Class<Type>?

    init {
        assert(this.coordinate != null) { "coordinates cant be null" }
        this.beforeGetter = this.coordinate.getter(true, before)
        this.afterGetter = this.coordinate.getter(false, after)
        this.type = afterGetter.get()?.javaClass ?: beforeGetter.get()?.javaClass
    }

    override fun sameObject(): Boolean {
        return beforeGetter.get() === afterGetter.get()
    }

    override fun sameClass(): Boolean {
        return sameObject() || bothNotNull() && beforeGetter.get()?.javaClass == afterGetter.get()?.javaClass
    }

    override fun bothNotNull(): Boolean {
        return beforeGetter.get() != null && afterGetter.get() != null
    }

    override fun coordinate(): Coordinate<Type, Parent> {
        return coordinate
    }

    override fun before(): Type? {
        return beforeGetter.get()
    }

    override fun after(): Type? {
        return afterGetter.get()
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 97 * hash + Objects.hashCode(this.beforeGetter.get())
        hash = 97 * hash + Objects.hashCode(this.afterGetter.get())
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as PairRelative<*, *>?
        if (this.beforeGetter.get() != other!!.beforeGetter.get()) {
            return false
        }
        if (this.afterGetter.get() != other.afterGetter.get()) {
            return false
        }
        return true
    }

    override fun type(): Class<Type>? {
        return type
    }

}
