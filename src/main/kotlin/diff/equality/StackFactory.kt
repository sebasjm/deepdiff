package diff.equality

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 * *
 * @param <Type>
</Type> */
interface StackFactory<Type> {

    fun newStack(): Stack<Type>

}
