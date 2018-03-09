package diff.equality

/**
 * Created by sebasjm on 08/03/18.
 */
class DamerauLevenshtein<Type: Any>(
    val comparator: java.util.Comparator<Type> = Comparator<Type> { o1, o2 -> if (o1 === o2) 0 else 1 },
    val selector: (Type) -> Int = { o1 : Type -> o1.hashCode() }
) {
    val lcs = LongestCommonSubsequence<Pair<Type>>(Comparator<Pair<Type>> { o1: Pair<Type>, o2: Pair<Type> -> comparator.compare(o1.element, o2.element)})

    class Pair<Type>(val index: Int, val element: Type, val selector: (Type) -> Int, val compare: Comparator<Type>): Comparable<Pair<Type>> {
        override fun equals(other: Any?) : Boolean {
            if (other == null) return false
            val type = other as Pair<Type>?
            if (type == null) return false
            val element1 = this.element
            if (element1 == null) return type.element == null
            return compare.compare(element1,type.element).equals(0)
        }
        override fun hashCode(): Int {
            return selector(this.element)
        }

        override fun compareTo(other: Pair<Type>): Int {
            return compare.compare(this.element, other.element) / Math.abs(other.index - this.index)
        }
    }

    interface Listener {
        fun removed(x: Int)
        fun added(x: Int)
        fun same(x: Int, y: Int)
        fun moved(x: Int, y: Int)
        fun modified(x: Int, y: Int)
        fun movedAndModified(x: Int, y: Int)
    }

    data class Action(val x: Int?, val y: Int?, val action : ()->Unit) : Comparable<Action> {
        override fun compareTo(other: Action): Int {
            val ax = this.x ?: this.y!!
            val ay = this.y ?: this.x!!
            val bx = other.x ?: other.y!!
            val by = other.y ?: other.x!!
            return (ax*10+ay).compareTo(bx*10+by)

//            val x = this.x?.compareTo(other.x ?: 0)
//            val y = this.y?.compareTo(other.y ?: 0)
//            return x?.compareTo(y?:0) ?: 0
        }
    }

    fun reduceAndSolve(a: List<Type>, b: List<Type>, lis: Listener) {

        val ap = a.mapIndexed { i, e -> Pair(i, e, selector, comparator) }.sorted()
        val bp = b.mapIndexed { i, e -> Pair(i, e, selector, comparator) }.sorted()

        val actionList = mutableListOf<Action>()

        lcs.reduceAndSolve(ap, bp, object : LCSMatrix.Listener {
            override fun same(x: Int, y: Int) {
                val before = ap[x]
                val after = bp[y]

                val sameIndex = before.index.equals(after.index)
                val areEquals = before.element.equals(after.element)
                if (sameIndex.not() && areEquals.not()) {
                    actionList.add(Action(before.index, after.index, { lis.movedAndModified(before.index, after.index) }))
                } else if (sameIndex && areEquals.not()) {
                    actionList.add(Action(before.index, after.index, { lis.modified(before.index, after.index) }))
                } else if (areEquals && sameIndex.not()) {
                    actionList.add(Action(before.index, after.index, { lis.moved(before.index, after.index) }))
                } else {
                    actionList.add(Action(before.index, after.index, { lis.same(before.index, after.index) }))
                }
            }

            override fun add(x: Int) {
                actionList.add(Action(null, bp[x].index, { lis.added(bp[x].index) }))
            }

            override fun del(y: Int) {
                actionList.add(Action(ap[y].index, null, { lis.removed(ap[y].index) }))
            }
        })

        actionList.sort()
        actionList.forEach { it.action() }
    }

    companion object {

        internal val lcs: DamerauLevenshtein<Char> = DamerauLevenshtein<Char>(
            comparator = Comparator<Char> { a, b -> a.toLowerCase().compareTo(b.toLowerCase()) },
            selector = { c -> c.toLowerCase().hashCode() }
        )

        fun run1(before: String, after: String): String {
            println(String.format("compraing: %1s - %2s", before, after))
            val builder = StringBuilder()
            val _before = before.toList()
            val _after = after.toList()

            lcs.reduceAndSolve(_before, _after, object : Listener {
                override fun added(x: Int) {
                    println("- $x add: ${_after[x]}")
                }

                override fun removed(y: Int) {
                    println("$y - del: ${_before[y]}")
                }

                override fun same(x: Int, y: Int) {
                    println("$x $y same: ${_before[x]} | ${_after[y]}")
                    builder.append(x).append(",").append(y).append(": ").append(_before[x]).append("|")
                }

                override fun modified(x: Int, y: Int) {
                    println("$x $y mod: ${_before[x]} | ${_after[y]}")
                    builder.append(x).append(",").append(y).append(": ").append(_before[x]).append("|")
                }

                override fun movedAndModified(x: Int, y: Int) {
                    println("$x $y moved&mod: ${_before[x]} | ${_after[y]}")
                    builder.append(x).append(",").append(y).append(": ").append(_before[x]).append("|")
                }

                override fun moved(x: Int, y: Int) {
                    println("$x $y moved: ${_before[x]} | ${_after[y]}")
                    builder.append(x).append(",").append(y).append(": ").append(_before[x]).append("|")
                }
            })
            println("\"$builder\"")
            return builder.toString()
        }

        fun run(before: String, after: String, expected: String) {
            val result = run1(before, after)
            if (result != expected) println("not as expected: \nres:$result | \nexp:$expected")
        }

        fun test() {
            run("sebastian", "javier", "6,3: i|3,1: a|")
            run("sebastian", "seBAStian", "8,8: n|7,7: a|6,6: i|5,5: t|4,4: s|3,3: a|2,2: b|1,1: e|0,0: s|")
            run("sebastian", "sebastia", "7,7: a|6,6: i|5,5: t|4,4: s|3,3: a|2,2: b|1,1: e|0,0: s|")
            run("sebastian", "javi", "6,3: i|3,1: a|")
            run("sebastian", "stian", "8,4: n|7,3: a|6,2: i|5,1: t|0,0: s|")

            run("abcd", "abd", "3,2: d|1,1: b|0,0: a|")
            run("abcd", "abdc", "3,2: d|1,1: b|0,0: a|")
            run("abcd", "ac", "2,1: c|0,0: a|")

            run("hola como te va?", "hola te va?", "15,10: ?|14,9: a|13,8: v|12,7:  |11,6: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
            run("hola como te va?", "hola COMO te re va?", "15,18: ?|14,17: a|13,16: v|12,12:  |11,11: e|10,10: t|9,9:  |8,8: o|7,7: m|6,6: o|5,5: c|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
            run("hola como te va?", "hola te re va?", "15,13: ?|14,12: a|13,11: v|12,10:  |11,9: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
            run("hoRE como te va?", "hola te re va?", "15,13: ?|14,12: a|13,11: v|12,10:  |11,9: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|")
        }

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size == 2) {
                run1(args[0], args[1])
            } else {
                test()
            }
        }
    }

}