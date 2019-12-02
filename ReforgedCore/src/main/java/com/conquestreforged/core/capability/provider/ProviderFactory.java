package com.conquestreforged.core.capability.provider;

@FunctionalInterface
public interface ProviderFactory<T> {

    Provider<T> create();
}
