package diff.patch.accessors;

import diff.patch.Getter;

import java.lang.reflect.Field;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class FieldGetter<Type> implements Getter<Type> {
    final Field field;
    final Object target;

    public FieldGetter(Object target, Field field) {
        this.field = field;
        this.target = target;
        field.setAccessible(true);
    }

    @Override
    public Type get() {
        try {
            return (Type) field.get(target);
        } catch (IllegalArgumentException | IllegalAccessException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
    
}
