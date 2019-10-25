package com.cjburkey.jgraph.prop;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

/**
 * A property that represents a mutable value that may have listeners attached.
 *
 * @param <T> The type of the value of this property
 * @since 0.1.0
 */
@SuppressWarnings({"UnusedReturnValue", "unused", "BooleanMethodIsAlwaysInverted", "WeakerAccess"})
public class Property<T> {

    private final ArrayList<PropListener<T>> listeners = new ArrayList<>();
    private T value;

    /**
     * Initialize this property with the provided value.
     *
     * @param value The default value
     */
    public Property(T value) {
        this.value = value;
    }

    /**
     * Initialize this property with no value.
     */
    public Property() {
        this(null);
    }

    /**
     * Check if the value is null.
     *
     * @return Whether the backing value is null.
     */
    public boolean has() {
        return value != null;
    }

    /**
     * Get the value.
     *
     * @return Get the backing value (or a reference to the value depending on the type, obviously).
     */
    public T get() {
        return value;
    }

    /**
     * Update the value to the provided value. This will also trigger all of
     * the listeners. If the new value is equal to (either by reference or by
     * {@code .equals()}, then nothing will happen.
     *
     * @param newValue The new value.
     */
    public void set(T newValue) {
        if (Objects.equals(value, newValue)) return;

        T oldValue = this.value;

        // Update this property's value in case any listeners use `get()` for
        // some reason (they really really shouldn't but this is here because
        // it was going to be possible to update a property when any of a few
        // properties changed and it wouldn't be known if the value member had
        // been updated yet).
        value = newValue;

        // Update all the listeners
        listeners.forEach(listener -> {
            if (listener != null) {
                listener.onUpdate(oldValue, newValue);
            }
        });
    }

    /**
     * Update this property's value according to the provided function.
     *
     * @param function The function that takes in the current value of the property and returns the new value.
     * @return This property for chaining.
     */
    public Property<T> map(Function<T, T> function) {
        set(function.apply(value));
        return this;
    }

    /**
     * Attach a listener to this property that will be updated when/if the
     * value is updated to a new, non-equal value.
     *
     * @param listener The listener to be executed upon changes.
     * @return The identifier for this listener so it may be removed later.
     */
    public int listen(PropListener<T> listener) {
        listeners.add(listener);
        return listeners.size() - 1;
    }

    public static void listenAll(PropListener<Object> listener, Property<?>... properties) {
        for (Property property : properties) {
            //noinspection unchecked
            property.listen(listener);
        }
    }

    /**
     * Remove a listener from this property so that it is no longer updated
     * when changes occur.
     *
     * @param id The identifier of the listener to be removed.
     */
    public void unlisten(int id) {
        // Cache the size because it looks nicer I guess (no real reason for
        // this).
        final int SIZE = listeners.size();

        if (id == SIZE - 1) {
            // If this is the last listener within the list, we can just
            // removed to decrease the size.
            listeners.remove(id);
        } else if (id >= 0 && id < SIZE) {
            // If this is not the last listener in the list, just set it to
            // null so that other identifiers remain the same.
            listeners.set(id, null);
        }
    }

    /**
     * Remove all the listeners for this property.
     */
    public void unlistenAll() {
        listeners.clear();
    }

    /**
     * Bind this property to another property. When the other property's
     * value changes, this property's value will be updated according to the
     * function provided.
     *
     * @param other    The property to which this property should be bound.
     * @param function A function that takes the other property's value and returns the new value for this property.
     */
    public void bindTo(Property<T> other, Function<T, T> function) {
        other.listen((oldValue, newValue) -> set(function.apply(newValue)));
    }

    /**
     * Bind this property to another property. When the other property's
     * value changes, this property's value will be updated to the same value.
     *
     * @param other The property to which this property should be bound.
     */
    public void bindTo(Property<T> other) {
        // Just bind with the identity function which is just `val -> val`
        bindTo(other, Function.identity());
    }

}
