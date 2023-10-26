package io.randomthoughts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class Utils {
    public static <T> T readJsonFile(Path jsonFilePath, TypeReference<T> type) {
        try {
            var json = new String(Files.readAllBytes(jsonFilePath));
            var mapper = new ObjectMapper();

            return mapper.readValue(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T ifNull(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static <T> T ifNull(Supplier<T> valueSupplier, Supplier<T> defaultValueSupplier) {
        var value = valueSupplier.get();

        return value == null ? defaultValueSupplier.get() : value;
    }

    public static void pause(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static void attempt(CheckedRunnable<Exception> runnable) {
        try {
            runnable.run();
        } catch (Exception ignored) {
        }
    }

    public static long random(long upper) {
        return (long) random((double) upper);
    }

    public static int random(int upper) {
        return (int) random((double) upper);
    }

    public static double random(double upper) {
        return Math.random() * upper;
    }

    public static void deleteFile(File file) {
        runPowerShellCommand("Remove-Item -Force -Recurse -Path \"" + file.getAbsolutePath() + "\"");
    }

    public static void runPowerShellCommand(String command) {
        runPowerShellCommand(command, null);
    }

    public static void runPowerShellCommand(String command, String workingDirectory) {
        final var cmd = "pwsh -Command \"" + command.replace("\"", "\"\"") + "\"";
        final var dir = null == workingDirectory ? System.getProperty("user.dir") : workingDirectory;

        attempt(() -> Runtime.getRuntime().exec(cmd, null, Paths.get(dir).toFile()).waitFor());
    }
}
