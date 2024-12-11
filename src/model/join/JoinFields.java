package model.join;

import java.util.List;
import java.util.function.Supplier;

public class JoinFields<T> {

    private final List<Long> ids;
    private final Supplier<List<T>> loader;
    private List<T> value = null;

    public JoinFields(List<Long> ids, Supplier<List<T>> loader) {
        this.ids = ids;
        this.loader = loader;
    }

    public List<Long> getIds() {
        return ids;
    }

    public List<T> getValue() {
        if (value == null) {
            value = loader.get();
        }

        return value;
    }
}
