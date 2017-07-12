package diff.patch.accessors

import diff.patch.Getter

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class ClassGetter(internal val target: Class<Any>) : Getter<Class<Any>> {

    override fun get(): Class<Any>? {
        return target
    }

}
