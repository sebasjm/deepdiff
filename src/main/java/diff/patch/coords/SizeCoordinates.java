package diff.patch.coords;

import diff.patch.Coordinate;
import diff.patch.Getter;
import diff.patch.Setter;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */
public class SizeCoordinates<Type> extends RelativeCoordinates<Integer, Type> {

    static public enum SizeType {
        ARRAY,
        FIELD,
        LIST,
        MAP,
        SET,
        ;
        
        Getter<Integer> buildGetter(final Object target) {
            switch (this) {
                case ARRAY : return new Getter<Integer>() {
                    @Override public Integer get() {
                        return Array.getLength(target);
                    }
                };
                case FIELD : return new Getter<Integer>() {
                    @Override public Integer get() {
                        return target.getClass().getFields().length;
                    }
                };
                case LIST  : return new Getter<Integer>() {
                    @Override public Integer get() {
                        return ((List)target).size();
                    }
                };
                case MAP   : return new Getter<Integer>() {
                    @Override public Integer get() {
                        return ((Map)target).size();
                    }
                };
                case SET   : return new Getter<Integer>() {
                    @Override public Integer get() {
                        return ((Set)target).size();
                    }
                };
            }
            return null;
        }
    }
    
    final SizeType type;
    
    public SizeCoordinates(Coordinate parent, SizeType type) {
        super(parent);
        this.type = type;
        assert type != null;
    }

    @Override
    public String relativeName() {
        return ":size";
    }

    @Override
    public Getter<Integer> getter(Object target) {
        return type.buildGetter(target);
    }

    @Override
    public Setter<Integer> setter(Object target) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean applies(Object target) {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SizeCoordinates<?> other = (SizeCoordinates<?>) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    
    
}
