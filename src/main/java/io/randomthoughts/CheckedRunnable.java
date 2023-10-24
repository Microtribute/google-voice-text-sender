package io.randomthoughts;

@FunctionalInterface
public interface CheckedRunnable<E extends Exception> extends Runnable {

    @Override
    default void run() throws RuntimeException {
        try {
            runExceptionally();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void runExceptionally() throws E;
}