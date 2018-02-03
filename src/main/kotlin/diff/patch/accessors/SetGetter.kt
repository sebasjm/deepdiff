package diff.patch.accessors

import diff.patch.Getter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class SetGetter<Element: Any>(internal val set: Set<Element>, internal val element: Element?) : Getter<Element> {

    override fun get(): Element? {
        return if (element != null && set.contains(element)) element else null
    }

}
