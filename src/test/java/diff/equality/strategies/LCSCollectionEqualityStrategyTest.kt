package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.patch.coords.RootCoordinate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Created by sebasjm on 14/07/17.
 */
class LCSCollectionEqualityStrategyTest {

    @Test fun emptyListShouldBeEquals() {
        assertEquals(
            LCSCollectionEqualityStrategy().compare( emptyList(), emptyList() ),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun equalListShoudBeEquals() {
        assertEquals(
            LCSCollectionEqualityStrategy().compare( listOf(1,2,3), listOf(1,2,3) ),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun shouldReduceTheComparisonToARange1() {
        val before = listOf(1, 4, 3)
        val after = listOf(1, 2, 3)
        val compare = LCSCollectionEqualityStrategy().compare(before, after) as EqualityStrategy.UndecidibleMoreToCompare<List<Any>>
        assertEquals(compare.result.size , 1)
        assertEquals(compare.result.first()(RootCoordinate()).getter(true, before).get(), listOf(4))
        assertEquals(compare.result.first()(RootCoordinate()).getter(false, after).get(), listOf(2))
    }

    @Test fun shouldReduceTheComparisonToARange2() {
        val before = listOf(1, 4, 5, 6, 3)
        val after = listOf(1,   2,      3)
        val compare = LCSCollectionEqualityStrategy().compare(before, after) as EqualityStrategy.UndecidibleMoreToCompare<List<Any>>
        assertEquals(compare.result.size , 1)
        assertEquals(compare.result.first()(RootCoordinate()).getter(true, before).get(), listOf(4,5,6))
        assertEquals(compare.result.first()(RootCoordinate()).getter(false, after).get(), listOf(2))
    }

    @Test fun shouldReduceTheComparisonToARange3() {
        val before = listOf(1, 4, 5, 6, 3)
        val after = listOf(1,     2, 7, 3)
        val compare = LCSCollectionEqualityStrategy().compare(before, after) as EqualityStrategy.UndecidibleMoreToCompare<List<Any>>
        assertEquals(compare.result.size , 1)
        assertEquals(compare.result.first()(RootCoordinate()).getter(true, before).get(), listOf(4,5,6))
        assertEquals(compare.result.first()(RootCoordinate()).getter(false, after).get(), listOf(2,7))
    }

    @Test fun shouldReduceTheComparisonToARange4() {
        val before = listOf(1,       3)
        val after  = listOf(1, 2, 7, 3)
        val compare = LCSCollectionEqualityStrategy().compare(before, after) as EqualityStrategy.UndecidibleMoreToCompare<List<Any>>
        assertEquals(compare.result.size , 1)
        assertEquals(compare.result.first()(RootCoordinate()).getter(true, before).get(), emptyList<Int>())
        assertEquals(compare.result.first()(RootCoordinate()).getter(false, after).get(), listOf(2,7))
    }

    @Test fun shouldReduceTheComparisonToMultipleRange1() {
        val before = listOf(1,       3, 4, 5, 6, 8,9)
        val after  = listOf(1, 2, 7, 3, 4      , 8,9)
        val compare = LCSCollectionEqualityStrategy().compare(before, after) as EqualityStrategy.UndecidibleMoreToCompare<List<Any>>
        assertEquals(compare.result.size , 2)
        assertEquals(compare.result[0](RootCoordinate()).getter(true, before).get(), listOf(5,6))
        assertEquals(compare.result[0](RootCoordinate()).getter(false, after).get(), emptyList<Int>())
        assertEquals(compare.result[1](RootCoordinate()).getter(true, before).get(), emptyList<Int>())
        assertEquals(compare.result[1](RootCoordinate()).getter(false, after).get(), listOf(2,7))
    }
}

