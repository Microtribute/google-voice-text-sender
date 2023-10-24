package io.randomthoughts;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        var quote = QuoteProvider.random();
        var credentials = UserProvider.getUsers();
        var gavin1 = credentials.get(0);

        System.out.println(quote);
        System.out.println(gavin1);

        try (var googleVoice = new GoogleVoice(gavin1)) {
            googleVoice.text(quote.toString());
        };
    }
}
