package io.randomthoughts;

import com.microsoft.playwright.*;

public class GoogleVoice implements AutoCloseable {
    private final User user;

    private final Playwright playwright;

    private final Page page;

    public GoogleVoice(User user) {
        this.user = user;

        var launchOptions = new BrowserType.LaunchOptions()
                .setTimeout(0)
                .setHeadless(false)
                .setChannel("msedge")
        ;

        var pageOptions = new Browser.NewPageOptions()
                .setScreenSize(1920, 1080)
        ;

        this.playwright = Playwright.create();
        this.page = playwright
                .chromium()
                .launch(launchOptions)
                .newPage(pageOptions)
        ;
    }

    public void text(String text) {
        authenticate();
    }

    private void authenticate() {
        page.navigate("https://voice.google.com");
        clickElement("a.signUpLink");
        clickElement("input#identifierId").fill(user.getEmail());
        clickElement("div#identifierNext button").click();
        Utils.pause(10000);
    }

    private ElementHandle clickElement(String selector) {
        var element = page.waitForSelector(selector);

        if (element != null) {
            var box = element.boundingBox();
            var x = box.x + Utils.random(box.width);
            var y = box.y + Utils.random(box.height);
            var steps = Utils.random(10);

            page.mouse().move(x, y, new Mouse.MoveOptions().setSteps(steps));
            element.click();
        }

        return element;
    }

    @Override
    public void close() {
        this.playwright.close();
    }
}
