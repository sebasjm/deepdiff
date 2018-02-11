package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter

import java.lang.reflect.Array
import java.util.Objects

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 */
class SizeCoordinates<Type: Any>(parent: Coordinate<Type, Any>, internal val type: SizeCoordinates.SizeType) : RelativeCoordinates<Int, Type>(parent) {

    enum class SizeType {
        ARRAY,
        FIELD,
        LIST,
        MAP,
        SET;

        internal fun buildGetter(target: Any): Getter<Int> {
            when (this) {
                ARRAY -> return object : Getter<Int> {
                    override fun get(): Int? {
                        return Array.getLength(target)
                    }
                }
                FIELD -> return object : Getter<Int> {
                    override fun get(): Int? {
                        return target.javaClass.fields.size
                    }
                }
                LIST -> return object : Getter<Int> {
                    override fun get(): Int? {
                        return (target as List<*>).size
                    }
                }
                MAP -> return object : Getter<Int> {
                    override fun get(): Int? {
                        return (target as Map<*, *>).size
                    }
                }
                SET -> return object : Getter<Int> {
                    override fun get(): Int? {
                        return (target as Set<*>).size
                    }
                }
            }
        }
    }

    override val name = parent.name + ":size"

    override fun getter(target: Type): Getter<Int> {
        return type.buildGetter(target)
    }

    override fun setter(target: Type): Setter<Int> {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun applies(target: Type): Boolean {
        return true
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 19 * hash + Objects.hashCode(this.type)
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as SizeCoordinates<*>?
        if (this.type != other!!.type) {
            return false
        }
        return true
    }


}
