package diff.patch.accessors

import diff.patch.Getter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class RangeListGetter<Element: Any>(internal val index: Int, internal val size: Int, internal val list: List<Element>) : Getter<List<Element>> {

    override fun get(): List<Element>? {
        val last = if (index + size > list.size) list.size else index + size
        return if (index < list.size) list.subList(index, last) else null
    }

}
