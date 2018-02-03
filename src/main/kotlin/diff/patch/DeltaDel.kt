/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diff.patch

/**

 * @author sebasjm
 */
class DeltaDel<Type>(val before: Type) : Delta<Type> {

    init {
        assert(before != null)
    }

    override fun toString() = "Del{before=$before}"

}
