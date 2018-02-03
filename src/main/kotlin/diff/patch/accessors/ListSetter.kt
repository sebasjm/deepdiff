package diff.patch.accessors

import diff.patch.Setter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class ListSetter<Element: Any>(internal val index: Int, internal val list: MutableList<Element>) : Setter<Element> {

    override fun set(value: Element) {
        if (index < list.size) {
            list[index] = value
        }
    }

}
