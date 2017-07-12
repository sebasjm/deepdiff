package diff.equality

import diff.equality.EqualityStrategy.CompareResult
import diff.equality.strategies.*
import java.util.*


/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class Matcher(internal val stackFactory: StackFactory<Pair<Any,Any>>, internal val differenceListener: DifferenceListener, vararg equalityStrategies: EqualityStrategy<Any>) {
    internal val equalityStrategies: Array<out EqualityStrategy<Any>> = equalityStrategies

    @JvmOverloads constructor(stackFactory: StackFactory<Pair<Any,Any>> = defaultStackFactory(), differenceListener: DifferenceListener = defaultDifferenceListener(), equalityStrategies: List<EqualityStrategy<Any>> = defaultEqualityStrategies()) : this(stackFactory, differenceListener, *equalityStrategies.toTypedArray()) {}

    fun equals(a: Any, b: Any): Boolean {
        val stack = stackFactory.newStack()
        stack.add(PairAbsolute(a, b))
        var result = true
        var listenerWantedToStop = false

        while (stack.hasNext() && not(listenerWantedToStop)) {
            val pair = stack.next()

            if (pair.sameObject()) {
                continue
            }

            if (not(pair.bothNotNull())) {
                result = false
                listenerWantedToStop = differenceListener.onOneIsNull(pair)
                continue
            }

            if (not(pair.sameClass())) {
                result = false
                listenerWantedToStop = differenceListener.onNotSameClass(pair)
                continue
            }

            var strategyFound = false
            var strategy: EqualityStrategy<Any>? = null
            val beforeClass = pair.type()!!
            var i = 0
            while (i < equalityStrategies.size && !strategyFound) {
                strategy = equalityStrategies[i]
                strategyFound = strategy.shouldUseThisStrategy(beforeClass)
                i++
            }
            if (!strategyFound) {
                strategy = fieldsEqualityStrategy
            }

            val compare = strategy!!.compare(pair.before()!!, pair.after()!!)
            if (compare === CompareResult.DIFFERENT) {
                result = false
                listenerWantedToStop = differenceListener.onDifference(pair)
                continue
            }

            val undecidible = compare as? EqualityStrategy.UndecidibleMoreToCompare<Any>?
            if (undecidible != null) {
                for (builder in undecidible.result) {
                    stack.add(PairRelative(
                        pair,
                        builder.invoke(pair.coordinate()!!),
                        pair.before()!!,
                        pair.after()!!)
                    )
                }
            }
        }

        return result
    }

    companion object {

        fun defaultStackFactory(): StackFactory<Pair<Any, Any>> {
            return object : StackFactory<Pair<Any, Any>> {
                override fun newStack(): Stack<Pair<Any, Any>> {
                    return UnrepeatedStack()
                }
            }
        }

        fun defaultDifferenceListener(): DifferenceListener {
            return object : DifferenceListener {
                override fun onDifference(pair: Pair<Any, Any>): Boolean {
                    return true
                }

                override fun onOneIsNull(pair: Pair<Any, Any>): Boolean {
                    return true
                }

                override fun onNotSameClass(pair: Pair<Any, Any>): Boolean {
                    return true
                }
            }
        }

        internal val defaultStrategies = arrayOf<EqualityStrategy<Any>>(
            ArrayEqualityStrategy(),
            FloatingPointEqualityStrategy() as EqualityStrategy<Any>,
            DoubleFloatingPointEqualityStrategy() as EqualityStrategy<Any>,
            OrderedCollectionEqualityStrategy() as EqualityStrategy<Any>,
            UnorderedCollectionEqualityStrategy<Set<*>>() as EqualityStrategy<Any>,
            OrderedMapEqualityStrategy() as EqualityStrategy<Any>,
            UnorderedMapEqualityStrategy() as EqualityStrategy<Any>,
            JavaLangEqualityStrategy(),
            EnumeratedFieldsEqualityStrategy()
        )

        fun defaultEqualityStrategies(): List<EqualityStrategy<Any>> {
            return Arrays.asList(*defaultStrategies)
        }

        internal val fieldsEqualityStrategy = FieldsEqualityStrategy()

        private fun not(value: Boolean): Boolean {
            return !value
        }
    }

}
