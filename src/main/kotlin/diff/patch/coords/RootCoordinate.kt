package diff.patch.coords

import diff.patch.Coordinate
import diff.patch.Getter
import diff.patch.Setter

/**
 * Created by sebasjm on 04/07/17.
 */
class RootCoordinate<Type: Any> : Coordinate<Type, Any> {
    override val name = ""

    override fun getter(target: Any): Getter<Type> {
        return object : Getter<Type> {
            override fun get(): Type? = target as Type?
        }
    }

    override fun setter(target: Any): Setter<Type> {
        return object :Setter<Type> {
            override fun set(value: Type): Unit = throw RuntimeException("root object cannot be modified")
        }
    }

    override fun applies(target: Any) = true
}
