package diff.patch.accessors

import diff.patch.Getter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class ListGetter<Element: Any>(internal val index: Int, internal val list: List<Element>) : Getter<Element> {

    override fun get(): Element? {
        return if (index < list.size) list[index] else null
    }

}
