package diff.patch.accessors;

import diff.patch.Setter;

import java.util.Set;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class SetSetter<Element> implements Setter<Element> {
    final Set<Element> set;
    final Element element;

    public SetSetter(Set<Element> set, Element element) {
        this.set = set;
        this.element = element;
    }

    @Override
    public void set(Element value) {
        if (element != null) {
            if (value == null) {
                set.remove(element);
            } else {
                set.add(element);
            }
        }
    }
    
}
