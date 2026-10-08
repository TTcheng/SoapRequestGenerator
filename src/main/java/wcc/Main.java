package wcc;

import javafx.application.Application;

/**
 * Entry point.
 *
 * <p>Deliberately <em>not</em> an {@link Application} subclass: the JavaFX launcher only
 * refuses to start ("JavaFX runtime components are missing") when the main class itself
 * extends {@code Application} and the JavaFX modules are on the class path instead of the
 * module path. Launching through this class keeps {@code java -jar}, an IDE run
 * configuration and {@code mvn javafx:run} all working.
 */
public class Main {

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
