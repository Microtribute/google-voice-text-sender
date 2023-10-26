package io.randomthoughts;

import com.microsoft.playwright.*;

import java.io.File;
import java.nio.file.Paths;

public class GoogleVoice implements AutoCloseable {
    private final User user;

    private final Playwright playwright;

    private final Page page;

    private final File userDataDirectory;

    public GoogleVoice(User user) {
        this.user = user;

        var launchOptions = new BrowserType.LaunchPersistentContextOptions()
            .setTimeout(0)
            .setHeadless(false)
            .setScreenSize(1920, 1080)
            .setBypassCSP(true)
            .setJavaScriptEnabled(true);

        userDataDirectory = Paths.get(System.getProperty("user.dir"), "userdata", user.getEmail()).toFile();

        if (!userDataDirectory.exists()) {
            var ignored = userDataDirectory.mkdirs();
        }

        playwright = Playwright.create();

        // Make sure you launch Firefox
        page = playwright.firefox().launchPersistentContext(userDataDirectory.toPath(), launchOptions).pages().get(0);
    }

    public Message text(String phoneNumber, String text) {
        authenticate();

        page.navigate("https://voice.google.com/u/0/messages");

        // Click the "Send New Message" button
        page.waitForSelector("div[gv-test-id=send-new-message]").click();

        // Fill in the phone #
        page.waitForSelector("input[gv-test-id=recipient-picker-input]").fill(phoneNumber);

        // Choose the first item from the autocomplete menu
        page.waitForSelector("section#contact-list > div").click();

        // Fill in the text
        page.waitForSelector("textarea[gv-test-id=gv-message-input]").fill(text);

        // Wait for the send button to get enabled
        Utils.pause(3);

        // Click the Send button
        page.waitForSelector("[gv-test-id=send_message]").click();

        Utils.pause(5);

        return new Message(user.getPhoneNumber(), phoneNumber, text);
    }

    private void authenticate() {
        page.navigate("https://voice.google.com/u/0/messages");

        // This means the user has no previous session and needs to log in fresh
        if (!page.url().equals("https://voice.google.com/u/0/messages")) {
            page.waitForSelector("a.signUpLink").click();
            page.waitForURL("https://accounts.google.com/v3/signin/identifier?*");
            page.waitForSelector("input#identifierId").fill(user.getEmail());
            page.waitForSelector("div#identifierNext button").click();
            page.waitForURL("https://accounts.google.com/v3/signin/challenge/pwd?*");
            page.waitForSelector("div#password input[type=password][name=Passwd]").fill(user.getPassword());
            page.waitForSelector("div#passwordNext button").click();
            page.waitForURL("https://voice.google.com/u/0/*");
        }
    }

    @Override
    public void close() {
        playwright.close();
        // Utils.deleteFile(userDataDirectory);
    }
}
