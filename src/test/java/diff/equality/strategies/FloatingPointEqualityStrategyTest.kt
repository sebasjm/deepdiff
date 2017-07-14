package diff.equality.strategies

import diff.equality.EqualityStrategy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Created by sebasjm on 14/07/17.
 */
class FloatingPointEqualityStrategyTest {

    @Test fun shouldBeDifferent() {
        assertEquals(
            FloatingPointEqualityStrategy().compare(1f,2f),
            EqualityStrategy.CompareResult.DIFFERENT
        )
    }

    @Test fun shouldBeEqual() {
        assertEquals(
            FloatingPointEqualityStrategy().compare(1f,1f),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun shouldBeEqualWithBigEpsilon() {
        assertEquals(
            FloatingPointEqualityStrategy(0.51).compare(1f,3f),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun shouldCompareFloat() {
        assertTrue( FloatingPointEqualityStrategy().shouldUseThisStrategy(Float::class.java) )
    }

    @Test fun shouldNotCompareDouble() {
        assertFalse( FloatingPointEqualityStrategy().shouldUseThisStrategy(Double::class.java as Class<Float>) )
    }

}