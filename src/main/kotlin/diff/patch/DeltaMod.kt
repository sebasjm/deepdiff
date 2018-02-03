/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diff.patch

/**

 * @author sebasjm
 */
class DeltaMod<Type>(val before: Type, val after: Type) : Delta<Type> {

    init {
        assert(before != null && after != null)
    }

    override fun toString() = "Mod{before=$before, after=$after}"

}
