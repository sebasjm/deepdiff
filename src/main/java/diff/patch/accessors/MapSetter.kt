package diff.patch.accessors

import diff.patch.Setter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class MapSetter<Key: Any, Value: Any>(internal val target: MutableMap<Key, Value>, internal val field: Key) : Setter<Value> {

    override fun set(value: Value) {
        target.put(field, value)
    }

}
