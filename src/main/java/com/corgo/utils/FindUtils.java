package com.corgo.utils;

import java.util.Collection;
import java.util.function.Predicate;

public final class FindUtils {
    public static <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }
}