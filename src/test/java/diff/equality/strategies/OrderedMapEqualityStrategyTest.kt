package diff.equality.strategies

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import diff.equality.EqualityStrategy
import diff.equality.Pair
import diff.equality.PairAbsolute
import diff.equality.PairRelative
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by sebasjm on 07/07/17.
 */
class OrderedMapEqualityStrategyTest {
    var strategy :  OrderedMapEqualityStrategy = OrderedMapEqualityStrategy()

    @BeforeEach
    fun setUp() {
        strategy = OrderedMapEqualityStrategy()
    }

    @Test fun shouldReturnEqualsWhenComparingTwoEmptyTreeMaps() {
        val before = TreeMap<String,String>() as TreeMap<Any,Any>
        val after = TreeMap<String,String>() as TreeMap<Any,Any>

        val compare = strategy.compare(before, after)

        assert( compare == EqualityStrategy.CompareResult.EQUALS )
    }

    @Test fun shouldReturnUndecidibleWhenMapsHasContents() {
        val before = TreeMap<String,String>() as TreeMap<Any,Any>
        val after = TreeMap<String,String>() as TreeMap<Any,Any>

        before.put("1","something")
        before.put("2","something")

        val compare = strategy.compare(before, after)
            as? EqualityStrategy.UndecidibleMoreToCompare<*> ?: throw AssertionError("Assertion failed")

        assertEquals( compare.result.size, before.size * 2 )
    }

    @Test fun shouldQueueOrderComparisonAndKeyComparisonForOrderedMaps() {
        val before = TreeMap<String,String>() as TreeMap<Any,Any>
        val after = TreeMap<String,String>() as TreeMap<Any,Any>

        before.put("1","something")
        before.put("2","something")

        val compare = strategy.compare(before, after)
            as? EqualityStrategy.UndecidibleMoreToCompare<*> ?: throw AssertionError("Assertion failed")

        assertEquals( compare.result.size, before.size * 2 )
    }

    @Test fun shouldCompareTreeMap() {
        val map = TreeMap<String,String>() as SortedMap<Any,Any>

        assertTrue( strategy.shouldUseThisStrategy(map.javaClass) )
    }

}