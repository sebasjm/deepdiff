package diff

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.Collections
import java.util.HashSet
import java.util.concurrent.ConcurrentHashMap

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */

fun Class<*>.getDeepDeclaredFields(): Set<String> {
    val set = Reflect._reflectedFields[this]
    if (set != null) return set

    val fields = HashSet<String>()
    var it: Class<*>? = this
    while (it != null) {
        it.getDeclaredFields(fields)
        it = it.superclass
    }
    Reflect._reflectedFields.put(this, Collections.unmodifiableSet(fields))
    return fields
}

fun Class<*>.getDeclaredFields(fields: MutableSet<String>) {
    val elements = Reflect._reflectedFields[this]
    if (elements != null) {
        fields.addAll(elements)
        return
    }

    this.declaredFields
        .filter { it.isSerializable() }
        .onEach { if (!it.isAccessible) it.isAccessible = true }
        .mapTo(fields) { it.name }
}

fun Field.isSerializable() =
    !Modifier.isStatic(this.modifiers)
    && !this.name.startsWith("this$")
    && !Modifier.isTransient(this.modifiers)


object Reflect {
    internal val _reflectedFields = ConcurrentHashMap<Class<*>, Set<String>>()

    fun setDeclaredFieldsForClass(cls: Class<*>, vararg fields: String) {
        _reflectedFields.put(cls, HashSet<String>(fields.asList()))
    }

}
