package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.patch.Coordinate
import diff.patch.coords.FieldCoordinates
import java.util.*

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class EnumeratedFieldsEqualityStrategy : EqualityStrategy<Any> {

    /**
     * Object should have same fields

     * @param stack
     * *
     * @return
     */
    override fun compare(before: Any, after : Any): EqualityStrategy.CompareResult {
        val fieldsBefore = mapping[before.javaClass]!!
        val fieldsAfter = mapping[after.javaClass]!!
        if (fieldsBefore.isEmpty() && fieldsAfter.isEmpty()) return EqualityStrategy.CompareResult.EQUALS

        val result = fieldsAfter
            .filter { !fieldsBefore.contains(it) }
            .union( fieldsBefore )
            .map {
                { parent: Coordinate<Any, Any> -> FieldCoordinates<Any, Any>(it, parent) }
            }

        return EqualityStrategy.UndecidibleMoreToCompare(result)
    }

    override fun shouldUseThisStrategy(cls: Class<Any>) = mapping.containsKey(cls)

    companion object {

        //TODO: make this an instance property
        private val mapping = HashMap<Class<*>, MutableSet<String>>()

        fun setFieldForClass(cls: Class<*>, vararg fields: String) {
            mapping.put(cls,
                HashSet<String>(fields.asList())
            )
        }
    }

}
