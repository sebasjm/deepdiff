package diff.patch.accessors;

import diff.patch.Getter;
import diff.patch.Setter;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class SimpleGetterSetter<Type> implements Getter<Type>, Setter<Type> {
    Type obj;

    public SimpleGetterSetter(Type obj) {
        this.obj = obj;
    }

    @Override
    public Type get() {
        return obj;
    }

    @Override
    public void set(Type value) {
        obj = value;
    }
    
}
