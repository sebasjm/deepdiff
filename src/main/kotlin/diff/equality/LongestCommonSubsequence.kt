package diff.equality

import java.lang.Integer.max
import java.lang.Integer.min
import java.util.ArrayList
import java.util.Comparator
import kotlin.Pair

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */



class LongestCommonSubsequence<Type: Any> (
        val comparator: Comparator<Type> = Comparator<Type> { o1, o2 -> if (o1 === o2) 0 else 1 }
    ){

    fun solve(a: List<Type>, b: List<Type>): List<Match<Type>> {
        val result = ArrayList<Match<Type>>()

        LCSMatrix(a.size, b.size)
            .update(Comparator{ x,y -> comparator.compare(a[x],b[y]) })
            .walk(object: LCSMatrix.Listener{
                override fun same(x: Int, y: Int) {
                    result.add(Match(x, y, a[x]))
                }
                override fun add(x: Int) {}
                override fun del(x: Int) {}
            })

        return result
    }

    fun reduceAndSolve(a: List<Type>, b: List<Type>, lis: LCSMatrix.Listener) {
        val (min, max) = reduce(a, b)
        val hasDifferences = min != a.size

        val diff = a.size - b.size

        (a.size -1 downTo max).forEach{ lis.same(it, it - diff) }

        if (hasDifferences) {
            val A = a.subList(min,max)
            val B = b.subList(min,max - diff)

            LCSMatrix(A.size, B.size)
                .update(Comparator{ x,y -> comparator.compare(A[x],B[y])})
                .walk(ListenerDelegateWithMin(min, lis))
        }

        (min - 1 downTo 0).forEach { lis.same(it, it) }
    }

    internal fun reduce(a: List<Type>, b:List<Type>) : Pair<Int, Int> {
        val diff = a.size - b.size

        val min = (0 until min(a.size-1, b.size-1) )
            .takeWhile { comparator.compare(a[it], b[it]) == 0 }
            .size

        val max = a.size - (a.size -1 downTo max(min, diff + min) )
            .takeWhile { comparator.compare(a[it], b[it - diff]) == 0 }
            .size

        return Pair(min , max)
    }


    class Match<T>(internal val x: Int, internal val y: Int, internal val element: T)

    companion object {

        internal val lcs: LongestCommonSubsequence<Char> = LongestCommonSubsequence()

        fun run1(before: String, after: String): String {
            println(String.format("compraing: %1s - %2s", before, after))
            val builder = StringBuilder()
            val _before = before.toList()
            val _after = after.toList()

            lcs.reduceAndSolve(_before, _after, object : LCSMatrix.Listener {
                override fun same(x: Int, y: Int) {
                    println("$x $y same: ${_before[x]} | ${_after[y]}")
                    builder.append(x).append(",").append(y).append(": ").append(_before[x]).append("|")
                }

                override fun add(x: Int) {
                    println("- $x add: ${_after[x]}")
                }

                override fun del(y: Int) {
                    println("$y - del: ${_before[y]}")
                }
            })
            println("\"$builder\"")
            return builder.toString()
        }

        fun run2(before: String, after: String): String {
            println(String.format("compraing: %1s - %2s", before, after))
            val builder = StringBuilder()
            for (m in lcs.solve(before.toList(), after.toList())) {
                val search = String.format("%s,%s: %s", m.x, m.y, m.element.toString())
                println(search)
                builder.append(search).append("|")
            }
            println("\"" + builder.toString() + "\"")
            return builder.toString()
        }

        fun run(before: String, after: String, expected: String) {
            val result = run1(before, after)
            if (result != expected) throw RuntimeException("not as expected: \nres:$result | \nexp:$expected")
        }

        fun test() {
            run("sebastian", "javier", "6,3: i|3,1: a|")
            run("sebastian", "sebastian", "8,8: n|7,7: a|6,6: i|5,5: t|4,4: s|3,3: a|2,2: b|1,1: e|0,0: s|")
            run("sebastian", "sebastia", "7,7: a|6,6: i|5,5: t|4,4: s|3,3: a|2,2: b|1,1: e|0,0: s|")
            run("sebastian", "javi", "6,3: i|3,1: a|")
            run("sebastian", "stian", "8,4: n|7,3: a|6,2: i|5,1: t|0,0: s|")

            run("abcd", "abd", "3,2: d|1,1: b|0,0: a|")
            run("abcd", "abdc", "3,2: d|1,1: b|0,0: a|")
            run("abcd", "ac", "2,1: c|0,0: a|")

            run("hola como te va?", "hola te va?", "15,10: ?|14,9: a|13,8: v|12,7:  |11,6: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
            run("hola como te va?", "hola como te re va?", "15,18: ?|14,17: a|13,16: v|12,12:  |11,11: e|10,10: t|9,9:  |8,8: o|7,7: m|6,6: o|5,5: c|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
            run("hola como te va?", "hola te re va?", "15,13: ?|14,12: a|13,11: v|12,10:  |11,9: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
            run("hola como te va?", "hola te re va?", "15,13: ?|14,12: a|13,11: v|12,10:  |11,9: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
        }

        @JvmStatic fun main(args: Array<String>) {
            if (args.size == 2) {
                run1(args[0], args[1])
            } else {
                test()
            }
        }
    }

    class ListenerDelegateWithMin(val min :Int, val delegate: LCSMatrix.Listener): LCSMatrix.Listener {
        override fun same(x: Int, y: Int) {
            delegate.same(x+min, y+min)
        }

        override fun add(x: Int) {
            delegate.add(x+min)
        }

        override fun del(x: Int) {
            delegate.del(x+min)
        }
    }



}