package com.cjburkey.jgraph.prop;

@FunctionalInterface
public interface PropListener<T> {

    void onUpdate(T oldValue, T newValue);

}
