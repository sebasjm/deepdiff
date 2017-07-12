package diff.patch.accessors;

import diff.patch.Setter;

import java.lang.reflect.Field;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class FieldSetter<Type> implements Setter<Type> {
    final Field field;
    final Object target;

    public FieldSetter(Object target, Field field) {
        this.field = field;
        this.target = target;
        field.setAccessible(true);
    }

    @Override
    public void set(Type value) {
        try {
            field.set(target, value);
        } catch (IllegalArgumentException | IllegalAccessException ignored) {
            ignored.printStackTrace();
        }
    }
    
}
