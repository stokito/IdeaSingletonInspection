package com.github.stokito.IdeaSingletonInspection;

public class SingletonExample {

    private static SingletonExample INSTANCE;

    SingletonExample() {
    }

    static SingletonExample getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonExample();
        }
        return INSTANCE;
    }
}
