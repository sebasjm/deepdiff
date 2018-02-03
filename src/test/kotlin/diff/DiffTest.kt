package diff

import diff.patch.DeltaDel
import diff.patch.Patch
import diff.patch.coords.ArrayCoordinates
import diff.patch.coords.RootCoordinate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by sebasjm on 03/02/18.
 */
class DiffTest {
    @Test fun shouldFindDiff1() {
        val diff = Diff().list(arrayOf(1, 2, 3), arrayOf(1, 2))

        Assertions.assertEquals(
            CustomJson.niceWriter.writeValueAsString(diff),
            """[ {
  "coordinate" : "[2]",
  "delta" : {
    "before" : 3
  }
} ]""")
    }

    data class OtherObject(
        val pepe: String
    )
    data class SomeObject(
        val other: OtherObject,
        val papa: Int
    )

    @Test fun shouldFindDiff2() {
        val diff = Diff().list(SomeObject(OtherObject("1234"),2), SomeObject(OtherObject("12"),2))

        Assertions.assertEquals(
            CustomJson.niceWriter.writeValueAsString(diff),
            """[ {
  "coordinate" : ".other.pepe",
  "delta" : {
    "before" : "1234",
    "after" : "12"
  }
} ]""")
    }
}
