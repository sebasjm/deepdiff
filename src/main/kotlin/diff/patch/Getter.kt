package diff.patch

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
interface Getter<out Type : Any> {

    fun get(): Type?

    companion object {
        val nullObject = object : Getter<Any> {
            override fun get(): Any? {
                return null
            }
        }
    }

}
