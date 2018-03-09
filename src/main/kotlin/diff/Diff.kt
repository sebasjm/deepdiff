package diff

import diff.equality.*
import diff.equality.Stack
import diff.patch.*
import diff.patch.coords.ClassCoordinates
import diff.patch.coords.RelativeCoordinates

import java.util.*

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class Diff @JvmOverloads constructor(internal val defaultEqualityStrategies: List<EqualityStrategy<Any>> = Matcher.defaultEqualityStrategies()) {

    internal val stackFactory: StackFactory<Pair<Any, Any>>

    init {
        this.stackFactory = object : StackFactory<Pair<Any, Any>> {
            override fun newStack(): Stack<Pair<Any, Any>> {
                return SimpleStack()
            }
        }
    }

    private fun matcher(diffListener: DifferenceListener): Matcher {
        return Matcher(stackFactory, diffListener, defaultEqualityStrategies)
    }

    class CompareTree<ParentType: Any> @JvmOverloads constructor(val before: ParentType?, val after: ParentType?, internal val parent: CompareTree<Any>? = null, internal val coord: RelativeCoordinates<Any, Any>? = null) {
        internal val next: MutableMap<Coordinate<Any, ParentType>, CompareTree<Any>>
        internal val delta: MutableList<Delta<Any>>

        init {
            this.next = HashMap<Coordinate<Any, ParentType>, CompareTree<Any>>()
            this.delta = ArrayList<Delta<Any>>()
        }

        fun <SubType: Any> getParent(c: Class<SubType>): CompareTree<SubType>? {
            return parent as? CompareTree<SubType>
        }

        fun keys(): Set<Coordinate<Any, ParentType>> {
            return next.keys
        }

        fun getNext(coord: Coordinate<Any, ParentType>): CompareTree<Any> {
            return next[coord] as CompareTree<Any>
        }

        fun add(coord: Coordinate<Any, Any>?): CompareTree<Any> {
            if (coord == null) return this as CompareTree<Any>

            val relativeCoordinates = coord as RelativeCoordinates
            val parentByCoord = add( relativeCoordinates.parent() )

            val next = parentByCoord.next[coord]
            if (next != null) return next

            val thiz = CompareTree(
                coord.getter(true, parentByCoord.before!!).get(),
                coord.getter(false, parentByCoord.after!!).get(),
                parentByCoord,
                coord
            )
            parentByCoord.next.put(coord, thiz)
            return thiz
        }

        fun getDelta(): List<Delta<*>> {
            return delta
        }

        val fullName: String
            get() = coord?.name ?: ""

        fun walk(walker: CompareTreeWalker) {
            walk(0, walker)
        }

        protected fun walk(deep: Int, walker: CompareTreeWalker) {
            for (entry in this.keys()) {
                val subTree = getNext(entry)
                val skip = walker.onNode(deep,
                    subTree.before!!,
                    subTree.after!!,
                    subTree.getDelta()
                )
                if (skip) continue
                subTree.walk(deep + 1, walker)
            }
        }
    }

    interface CompareTreeWalker {
        fun onNode(deep: Int, before: Any, after: Any, delta: List<Delta<*>>): Boolean
    }

    fun <Type: Any> tree(before: Type, after: Type): CompareTree<Type> {
        val root = CompareTree(before, after)
        matcher(object : DifferenceListener {
            override fun onOneIsNull(pair: Pair<Any, Any>): Boolean {
                root.add(pair.coordinate())
                    .delta.add(if (pair.before() == null)
                    DeltaAdd(pair.after()!!)
                else
                    DeltaDel(pair.before()!!)
                )
                return false
            }

            override fun onNotSameClass(pair: Pair<Any, Any>): Boolean {
                root.add(ClassCoordinates(pair.coordinate()) as Coordinate<Any, Any>)
                    .delta.add(
                    DeltaMod(pair.before()!!.javaClass, pair.after()!!.javaClass)
                )
                return false
            }

            override fun onDifference(pair: Pair<Any, Any>): Boolean {
                root.add(pair.coordinate())
                    .delta.add(
                    DeltaMod(pair.before()!!, pair.after()!!)
                )
                return false
            }
        }).equals(before, after)
        return root
    }

    fun list(before: Any, after: Any): List<Patch<Any>> {
        val result = ArrayList<Patch<Any>>()
        matcher(object : DifferenceListener {
            override fun onOneIsNull(pair: Pair<Any, Any>): Boolean {
                result.add(Patch(
                    pair.coordinate(),
                    pair.type()!!,
                    if (pair.before() == null) DeltaAdd(pair.after()) else DeltaDel(pair.before())
                ))
                return false
            }

            override fun onNotSameClass(pair: Pair<Any, Any>): Boolean {
                result.add(Patch(
                    ClassCoordinates(pair.coordinate()),
                    pair.type()!!,
                    DeltaMod(pair.before(), pair.after())
                ))
                return false
            }

            override fun onDifference(pair: Pair<Any, Any>): Boolean {
                result.add(Patch(
                    pair.coordinate(),
                    pair.type()!!,
                    DeltaMod(pair.before(), pair.after())
                ))
                return false
            }
        }).equals(before, after)
        return result
    }

}
