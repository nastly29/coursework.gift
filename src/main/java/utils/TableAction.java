package utils;

import domain.sweets.Sweets;
import java.util.function.Consumer;

public class TableAction {
    private final String name;
    private final Consumer<Sweets> handler;

    public TableAction(String name, Consumer<Sweets> handler) {
        this.name = name;
        this.handler = handler;
    }

    public String getName() { return name; }

    public Consumer<Sweets> getHandler() { return handler; }
}