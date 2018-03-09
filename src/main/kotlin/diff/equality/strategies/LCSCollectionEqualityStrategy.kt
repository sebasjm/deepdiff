package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.LCSMatrix
import diff.equality.LongestCommonSubsequence
import diff.patch.Coordinate
import diff.patch.coords.ListCoordinates
import diff.patch.coords.RangeListCoordinates
import diff.patch.coords.RelativeCoordinates

/**
 * Created by sebasjm on 09/03/18.
 */
class LCSCollectionEqualityStrategy : EqualityStrategy<List<Any>> {

    val delegate = OrderedCollectionEqualityStrategy()

    override fun compare(before: List<Any>, after: List<Any>): EqualityStrategy.CompareResult {
        if (before.isEmpty() && after.isEmpty()) return EqualityStrategy.CompareResult.EQUALS


        val result : MutableList<(parent: Coordinate<List<Any>, Any>) -> RelativeCoordinates<Any, List<Any>>> = ArrayList()
        var atLeastOneIsDifferent = false
        LongestCommonSubsequence<Any>()
            .reduceAndSolve(before, after, object : LCSMatrix.Listener {
                var bix = before.size
                var aix = after.size
                var bsz = 0
                var asz = 0
                fun reset(x: Int, y: Int) { bix = x; aix = y; bsz = 0; asz = 0 }

                fun function(a:Int,b:Int,c:Int,d:Int) =
                    { parent: Coordinate<List<Any>, Any> -> RangeListCoordinates(a,b,c,d, parent) as RelativeCoordinates<Any, List<Any>> }

                override fun same(x: Int, y: Int) {
                    if (bsz > 0 || asz > 0) {
                        result.add(function(x+1,y+1,bsz,asz))
                    }

                    reset(x,y)
                }
                override fun add(x: Int) {
                    asz++
                    atLeastOneIsDifferent = true
                }
                override fun del(y: Int) {
                    bsz++
                    atLeastOneIsDifferent = true
                }
            })

        return if (result.isEmpty()) {
            if (atLeastOneIsDifferent) delegate.compare(before, after) else EqualityStrategy.CompareResult.EQUALS
        } else {
            EqualityStrategy.UndecidibleMoreToCompare(result)
        }
    }

    override fun shouldUseThisStrategy(cls: Class<List<Any>>): Boolean {
        return List::class.java.isAssignableFrom(cls)
    }
}
