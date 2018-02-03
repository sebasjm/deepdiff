package diff.patch

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
interface Setter<Type: Any> {

    fun set(value: Type)

    companion object {
        val nullObject: Setter<Any> = object : Setter<Any> {
            override fun set(value: Any) {}
        }
    }

}
