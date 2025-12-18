package com.example.cinematickets.models;

import java.lang.reflect.Type;

public interface Prototype<T> {
    T getClone();
}
