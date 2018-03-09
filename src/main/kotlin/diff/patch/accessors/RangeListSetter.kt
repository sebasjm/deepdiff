package diff.patch.accessors

import diff.patch.Setter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class RangeListSetter<Element: Any>(internal val index: Int, internal val size: Int, internal val list: MutableList<Element>) : Setter<List<Element>> {

    override fun set(value: List<Element>) {
        val last = if (index + size > list.size) list.size else index + size
        var i = 0;
        while (index + i < last && i < value.size) {
            list[index + i] = value[i]
            i++;
        }
    }

}
