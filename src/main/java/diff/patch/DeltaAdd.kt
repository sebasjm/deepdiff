/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diff.patch

/**

 * @author sebasjm
 */
class DeltaAdd<Type>(val after: Type) : Delta<Type> {

    init {
        assert(after != null)
    }

    override fun toString() = "Add{after=$after}"

}
