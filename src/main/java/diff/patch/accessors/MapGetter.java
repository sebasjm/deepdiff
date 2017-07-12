package diff.patch.accessors;

import diff.patch.Getter;

import java.util.Map;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class MapGetter<Key, Value> implements Getter<Value> {
    final Map<Key, Value> target;
    final Key field;

    public MapGetter(Map<Key, Value> target, Key field) {
        this.target = target;
        this.field = field;
    }

    @Override
    public Value get() {
        return target.get(field);
    }
    
}
