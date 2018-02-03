package diff.equality

import java.util.Deque
import java.util.HashSet
import java.util.LinkedList

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 * *
 * @param <Type>
</Type> */
class UnrepeatedStack<Type> : Stack<Type> {
    private val visited = HashSet<Type>()
    private val stack = LinkedList<Type>()
    private var current: Type? = null

    override fun next(): Type {
        val last = stack.removeFirst()
        current = last
        visited.add(last)
        return last
    }

    override fun getCurrent(): Type {
        return current!!
    }

    override fun hasNext(): Boolean {
        return !stack.isEmpty()
    }

    override fun add(obj: Type): UnrepeatedStack<Type> {
        if (!visited.contains(obj)) {
            stack.addFirst(obj)
        }
        return this
    }

}
