package diff.patch.accessors;

import diff.patch.Getter;

import java.util.Set;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class SetGetter<Element> implements Getter<Element> {
    final Set<Element> set;
    final Element element;

    public SetGetter(Set<Element> set, Element element) {
        this.set = set;
        this.element = element;
    }

    @Override
    public Element get() {
        return element != null && set.contains(element) ? element : null;
    }
    
}
