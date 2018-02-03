package diff.equality

import java.util.Comparator

class LCSMatrix(val sizeA: Int, val sizeB: Int, val S: Array<IntArray>, val R: Array<IntArray>) {
    private val NEITHER = 0
    private val UP = 1
    private val LEFT = 2
    private val UP_AND_LEFT = 3

    constructor(sizeA: Int, sizeB: Int) : this( sizeA, sizeB, Array(sizeA + 1) { IntArray(sizeB + 1) }, Array(sizeA + 1) { IntArray(sizeB + 1) }) {
        reset()
    }

    fun reset() {
        (0..sizeA).forEach {
            S[it][0] = 0
            R[it][0] = UP
        }
        (0..sizeB).forEach {
            S[0][it] = 0
            R[0][it] = LEFT
        }
    }

    fun update(comparator: Comparator<Int>) : LCSMatrix {
        (1..sizeA)
            .flatMap { x -> (1..sizeB).map { y -> Pair(x, y) } }
            .forEach { (x,y) ->
                if (comparator.compare(x - 1, y - 1) == 0) {
                    S[x][y] = S[x - 1][y - 1] + 1
                    R[x][y] = UP_AND_LEFT
                } else {
                    S[x][y] = S[x - 1][y - 1] + 0
                    R[x][y] = NEITHER
                }
                if (S[x - 1][y] >= S[x][y]) {
                    S[x][y] = S[x - 1][y]
                    R[x][y] = UP
                }
                if (S[x][y - 1] >= S[x][y]) {
                    S[x][y] = S[x][y - 1]
                    R[x][y] = LEFT
                }
            }
        return this
    }

    internal fun walk(listener: Listener) {
        var x = sizeA
        var y = sizeB
        while (x > 0 || y > 0) {
            when (R[x][y]) {
                UP_AND_LEFT -> {
                    x--
                    y--
                    listener.same(x, y)
                }
                UP -> {
                    x--
                    listener.del(x)
                }
                LEFT -> {
                    y--
                    listener.add(y)
                }
            }
        }
    }

    interface Listener {
        fun same(x: Int, y: Int)
        fun add(x: Int)
        fun del(x: Int)
    }

}