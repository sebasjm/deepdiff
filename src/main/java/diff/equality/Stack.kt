package diff.equality

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 * *
 * @param <Type>
</Type> */
interface Stack<Type> : Iterator<Type> {

    fun add(obj: Type): Stack<Type>

    fun getCurrent(): Type

}
