package io.randomthoughts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
