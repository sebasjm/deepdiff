package diff.equality.strategies

import diff.equality.EqualityStrategy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Created by sebasjm on 14/07/17.
 */
class DoubleFloatingPointEqualityStrategyTest {
    @Test fun shouldBeDifferent() {
        assertEquals(
            DoubleFloatingPointEqualityStrategy().compare(1.0,2.0),
            EqualityStrategy.CompareResult.DIFFERENT
        )
    }

    @Test fun shouldBeEqual() {
        assertEquals(
            DoubleFloatingPointEqualityStrategy().compare(1.0,1.0),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun shouldBeEqualWithBigEpsilon() {
        assertEquals(
            DoubleFloatingPointEqualityStrategy(0.51).compare(1.0,3.0),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun shouldCompareFloat() {
        assertTrue( DoubleFloatingPointEqualityStrategy().shouldUseThisStrategy(Double::class.java) )
    }

    @Test fun shouldNotCompareFloat() {
        assertFalse( DoubleFloatingPointEqualityStrategy().shouldUseThisStrategy(Float::class.java as Class<Double>) )
    }

}