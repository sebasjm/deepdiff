package diff.patch.accessors

import diff.patch.Setter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class SetSetter<Element: Any>(internal val set: MutableSet<Element>, internal val element: Element?) : Setter<Element> {

    override fun set(value: Element) {
        if (element != null) {
            if (value == null) {
                set.remove(element)
            } else {
                set.add(element)
            }
        }
    }

}
