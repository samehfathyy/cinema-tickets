package org.example.models;

import java.lang.reflect.Type;

public interface Prototype<T> {
    T getClone();
}
