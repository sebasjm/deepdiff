package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.getDeepDeclaredFields
import diff.patch.Coordinate
import diff.patch.coords.FieldCoordinates

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class FieldsEqualityStrategy<Type: Any> : EqualityStrategy<Type> {

    /**
     * Object should have same fields

     * @param stack
     * *
     * @return
     */

    override fun compare(before: Type, after: Type): EqualityStrategy.CompareResult {
        val fieldsBefore = before.javaClass.getDeepDeclaredFields()
        val fieldsAfter = after.javaClass.getDeepDeclaredFields()

        if (fieldsBefore.isEmpty() && fieldsAfter.isEmpty()) return EqualityStrategy.CompareResult.EQUALS

        val result = fieldsAfter
            .filter { !fieldsBefore.contains(it) }
            .union( fieldsBefore )
            .map { field ->
                { parent: Coordinate<Any,Any> -> FieldCoordinates<Any, Any>(field, parent)
                }
            }

        return EqualityStrategy.UndecidibleMoreToCompare(result)
    }

    override fun shouldUseThisStrategy(cls: Class<Type>) = true

}
