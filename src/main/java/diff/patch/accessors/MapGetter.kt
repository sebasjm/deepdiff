package diff.patch.accessors

import diff.patch.Getter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class MapGetter<Key, Value: Any>(internal val target: Map<Key, Value>, internal val field: Key) : Getter<Value> {

    override fun get(): Value? {
        return target[field]
    }

}
