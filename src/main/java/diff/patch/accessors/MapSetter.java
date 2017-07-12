package diff.patch.accessors;

import diff.patch.Setter;

import java.util.Map;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class MapSetter<Key, Value> implements Setter<Value> {
    final Map<Key, Value> target;
    final Key field;

    public MapSetter(Map<Key, Value> target, Key field) {
        this.target = target;
        this.field = field;
    }

    @Override
    public void set(Value value) {
        target.put(field, value);
    }
    
}
