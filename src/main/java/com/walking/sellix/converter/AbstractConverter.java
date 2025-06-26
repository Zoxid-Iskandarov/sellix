package com.walking.sellix.converter;

public abstract class AbstractConverter<S, T> {
    public T convert(S source) {
        throw new UnsupportedOperationException("convert(S source) is not supported for this converter");
    }

    public T convert(S source, T target) {
        throw new UnsupportedOperationException("convert(S source, T target) is not supported for this converter");
    }
}
