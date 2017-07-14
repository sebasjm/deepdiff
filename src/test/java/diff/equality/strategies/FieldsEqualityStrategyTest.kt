package diff.equality.strategies

import diff.equality.EqualityStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Created by sebasjm on 14/07/17.
 */
class FieldsEqualityStrategyTest {

    class Example
    class ExampleWithProperties(val string: String, val number: Int)

    @Test fun shouldBeEqualWithNoProperties() {
        assertEquals(
            FieldsEqualityStrategy<Example>().compare( Example(), Example() ),
            EqualityStrategy.CompareResult.EQUALS
        )
    }

    @Test fun shouldQueueAllPropertiesToCompare() {
        val result = FieldsEqualityStrategy<ExampleWithProperties>()
            .compare( ExampleWithProperties("str", 1), ExampleWithProperties("str", 2) )
                as? EqualityStrategy.UndecidibleMoreToCompare<*> ?: throw AssertionError("Assertion failed")

        assertEquals(result.result.size, 2)
    }

    @Test fun shouldCompareArbitraryClass() {
        assertTrue( FieldsEqualityStrategy<Example>().shouldUseThisStrategy(Example::class.java) )
    }
}