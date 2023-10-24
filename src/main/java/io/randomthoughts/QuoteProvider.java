package io.randomthoughts;

import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuoteProvider {
    private static final List<Quote> allQuotes = preloadAllQuotes();

    private static List<Quote> preloadAllQuotes() {
        var jsonFilePath = Paths.get(System.getProperty("user.dir"), "quotes.json");

        return Utils.ifNull(
            Utils.readJsonFile(jsonFilePath, new TypeReference<ArrayList<Quote>>() {}),
            List.of()
        );
    }

    public static Quote random() {
        return allQuotes.get(Utils.random(allQuotes.size()));
    }
}
