package diff.equality.strategies

import diff.equality.EqualityStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by sebasjm on 07/07/17.
 */
class OrderedMapEqualityStrategyTest {

    @Test fun shouldReturnEqualsWhenComparingTwoEmptyTreeMaps() {
        val before = TreeMap<String,String>() as TreeMap<Any,Any>
        val after = TreeMap<String,String>() as TreeMap<Any,Any>

        val compare = OrderedMapEqualityStrategy().compare(before, after)

        assert( compare == EqualityStrategy.CompareResult.EQUALS )
    }

    @Test fun shouldReturnUndecidibleWhenMapsHasContents() {
        val before = TreeMap<String,String>() as TreeMap<Any,Any>
        val after = TreeMap<String,String>() as TreeMap<Any,Any>

        before.put("1","something")
        before.put("2","something")

        val compare = OrderedMapEqualityStrategy().compare(before, after)
            as? EqualityStrategy.UndecidibleMoreToCompare<*> ?: throw AssertionError("Assertion failed")

        assertEquals( compare.result.size, before.size * 2 )
    }

    @Test fun shouldQueueOrderComparisonAndKeyComparisonForOrderedMaps() {
        val before = TreeMap<String,String>() as TreeMap<Any,Any>
        val after = TreeMap<String,String>() as TreeMap<Any,Any>

        before.put("1","something")
        before.put("2","something")

        val compare = OrderedMapEqualityStrategy().compare(before, after)
            as? EqualityStrategy.UndecidibleMoreToCompare<*> ?: throw AssertionError("Assertion failed")

        assertEquals( compare.result.size, before.size * 2 )
    }

    @Test fun shouldCompareTreeMap() {
        val map = TreeMap<String,String>() as SortedMap<Any,Any>

        assertTrue( OrderedMapEqualityStrategy().shouldUseThisStrategy(map.javaClass) )
    }

}