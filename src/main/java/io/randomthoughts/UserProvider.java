package io.randomthoughts;

import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Paths;
import java.util.List;

public class UserProvider {
    private static final List<User> users = loadUsers();

    private static List<User> loadUsers() {
        var jsonFilePath = Paths.get(System.getProperty("user.dir"), "users.json");

        return Utils.ifNull(
            Utils.readJsonFile(jsonFilePath, new TypeReference<>() {}),
            List.of()
        );
    }

    public static List<User> getUsers() {
        return users;
    }
}
