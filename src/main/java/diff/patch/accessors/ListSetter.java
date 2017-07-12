package diff.patch.accessors;

import diff.patch.Setter;

import java.util.List;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class ListSetter<Element> implements Setter<Element> {
    final int index;
    final List<Element> list;

    public ListSetter(int index, List<Element> list) {
        this.index = index;
        this.list = list;
    }

    @Override
    public void set(Element value) {
        if (index < list.size()) {
            list.set(index, value);
        }
    }
    
}
