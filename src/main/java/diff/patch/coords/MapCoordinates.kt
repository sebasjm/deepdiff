package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter
import diff.patch.accessors.MapGetter
import diff.patch.accessors.MapSetter
import java.util.Objects

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class MapCoordinates<Key: Any, Value: Any>(internal val field: Key, parent: Coordinate<Map<Key, Value>, Any>) : RelativeCoordinates<Value, Map<Key, Value>>(parent) {

    override fun getter(target: Map<Key, Value>): Getter<Value> {
        return MapGetter(target, field)
    }

    override fun setter(target: Map<Key, Value>): Setter<Value> {
        return MapSetter(target as MutableMap, field)
    }

    override fun relativeName(): String {
        return "['$field']"
    }

    override fun applies(target: Map<Key, Value>): Boolean {
        return target.containsKey(field)
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = 19 * hash + Objects.hashCode(this.field)
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as MapCoordinates<*, *>?
        if (this.field != other!!.field) {
            return false
        }
        return true
    }

}
