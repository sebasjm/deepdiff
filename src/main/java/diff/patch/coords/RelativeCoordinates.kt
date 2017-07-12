package diff.patch.coords

import diff.patch.Coordinate

/**

 * @author sebasjm <smarchano></smarchano>@primary.com.ar>
 * *
 * @param <Type>
 * *
 * @param <Parent>
</Parent></Type> */
abstract class RelativeCoordinates<Type: Any, Parent: Any>(internal val parent: Coordinate<Parent, Any>) : Coordinate<Type, Parent> {

    fun parent(): Coordinate<Parent, Any> {
        return parent
    }

    override val name = parent.name + relativeName()

    override fun toString(): String {
        return name
    }

    abstract fun relativeName(): String



}
