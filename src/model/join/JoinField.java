package model.join;

import java.util.function.Supplier;

public class JoinField<T> {

    private final long id;
    private final Supplier<T> loader;
    private T value = null;

    public JoinField(long id, Supplier<T> loader) {
        this.id = id;
        this.loader = loader;
    }

    public long getId() {
        return id;
    }

    public T getValue() {
        if (value == null) {
            value = loader.get();
        }

        return value;
    }
}
