package diff.equality

import java.util.Deque
import java.util.LinkedList

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <Type>
</Type> */
class SimpleStack<Type> : Stack<Type> {

    private val stack = LinkedList<Type>()
    private var _current: Type? = null

    override fun next(): Type {
        _current = stack.removeFirst()
        return _current!!
    }

    override fun getCurrent(): Type {
        return _current!!
    }

    override fun hasNext(): Boolean {
        return !stack.isEmpty()
    }

    override fun add(obj: Type): Stack<Type> {
        stack.addFirst(obj)
        return this
    }

}
