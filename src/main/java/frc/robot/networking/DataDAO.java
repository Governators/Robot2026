package frc.robot.networking;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class DataDAO<E> {
    private String title;
    private Supplier<E> s;
    private Consumer<E> c;

    public DataDAO(String title, Supplier<E> s, Consumer<E> c) {
        this.title = title;
        this.s = s;
        this.c = c;
    }

    public String getTitle() {
        return title;
    }

    public <T> T get() {
        return (T)s.get();
    }

    public void set(Object e) {
        c.accept((E)e);
    }
}
