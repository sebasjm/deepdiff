package diff.patch

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <Type>
 * *
 * @param <Parent>
</Parent></Type> */
interface Coordinate<Type: Any, Parent: Any> {

    fun getter(target: Parent): Getter<Type>
    fun setter(target: Parent): Setter<Type>

    val name: String

    fun applies(target: Parent): Boolean
}
