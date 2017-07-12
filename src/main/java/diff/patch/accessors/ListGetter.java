package diff.patch.accessors;

import diff.patch.Getter;

import java.util.List;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class ListGetter<Element> implements Getter<Element> {
    final int index;
    final List<Element> list;

    public ListGetter(int index, List<Element> list) {
        this.index = index;
        this.list = list;
    }

    @Override
    public Element get() {
        return index < list.size() ? list.get(index) : null;
    }
    
}
