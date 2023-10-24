package io.randomthoughts;

public class Quote {
    private String text;
    private String author;

    public String getText() {
        return this.text;
    }

    public String getAuthor() {
        return this.author;
    }

    @Override
    public String toString() {
        return this.text.concat(this.author == null || this.author.isBlank() ? "" : " " + this.author);
    }
}
