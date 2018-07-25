package pro.pie.me.corm;

import java.util.concurrent.atomic.AtomicReference;

/**
 * NullableImmutableHolder
 */
public final class NullableImmutableHolder<V> {

    private final AtomicReference<V> holder = new AtomicReference<>(null);

    public V get() {
        return holder.get();
    }

    public void set(V v) {
        if (!this.holder.compareAndSet(null, v)) {
            throw new IllegalStateException("Unable to set another non-null value.");
        }
    }
}
