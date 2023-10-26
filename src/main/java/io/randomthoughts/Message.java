package io.randomthoughts;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Message {
    private final String from;
    private final String to;
    private final String text;

    public Message(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }

    public String text() {
        return text;
    }
}
