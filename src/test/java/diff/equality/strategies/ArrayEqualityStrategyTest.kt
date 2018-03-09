package diff.equality.strategies

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * Created by sebasjm on 07/07/17.
 */
//@RunWith(JUnitPlatform::class)
public class ArrayEqualityStrategyTest : Spek({
    describe("a artifact factory") {

        it("should build a artifact") {
            val sum = 1 + 1
            println("yes $sum")
        }

    }
})