package io.randomthoughts;

public class Main {
    public static void main(String[] args) {
        var users = UserProvider.getUsers();
        var user1 = users.get(0);
        var user2 = users.get(1);

        try (var voice1 = new GoogleVoice(user1)) {
            var msg = voice1.text(user2.getPhoneNumber(), QuoteProvider.random().toString());
            System.out.printf("Message sent from %s to %s - %s\n", msg.getFrom(), msg.getTo(), msg.getText());
        };

        try (var voice2 = new GoogleVoice(user2)) {
            var msg = voice2.text(user1.getPhoneNumber(), QuoteProvider.random().toString());
            System.out.printf("Message sent from %s to %s - %s\n", msg.getFrom(), msg.getTo(), msg.getText());
        };
    }
}
