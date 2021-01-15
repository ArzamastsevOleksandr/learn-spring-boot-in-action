package learn.spring.boot.in.action;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static learn.spring.boot.in.action.MockMvcWebTestWithFullContextAndNoServer.*;
import static org.assertj.core.api.Assertions.assertThat;

//sudo apt-get update
//sudo apt-get install -y unzip xvfb libxi6 libgconf-2-4
//wget https://github.com/mozilla/geckodriver/releases/download/v0.29.0/geckodriver-v0.29.0-linux64.tar.gz
//tar xzf geckodriver-v0.29.0-linux64.tar.gz
//sudo mv geckodriver /usr/bin/geckodriver
//mkdir ~/selenium && cd ~/selenium
//wget https://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar
//wget http://www.java2s.com/Code/JarDownload/testng/testng-6.5.1.jar.zip
//unzip testng-6.5.1.jar.zip

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTest {

    static FirefoxDriver browser;

    @Value("${local.server.port}")
    String port;

    @BeforeAll
    static void beforeAll() {
        browser = new FirefoxDriver();
        browser.manage()
                .timeouts()
                .implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    void name() {
        String url = "http://localhost:" + port + "/" + READER;
        browser.get(url);
        assertThat("You have no books in your book list").isEqualTo(browser.findElementByTagName("div").getText());

        browser.findElementByName(TITLE).sendKeys(TITLE);
        browser.findElementByName(AUTHOR).sendKeys(AUTHOR);
        browser.findElementByName(ISBN).sendKeys(ISBN);
        browser.findElementByName(DESCRIPTION).sendKeys(DESCRIPTION);

        browser.findElementByTagName("form").submit();

        WebElement webElement1 = browser.findElementByCssSelector("dt.bookHeadLine");
        assertThat(webElement1.getText()).contains(TITLE + " by " + AUTHOR + " (ISBN: " + ISBN + ")");

        WebElement webElement2 = browser.findElementByCssSelector("dd.bookDescription");
        assertThat(webElement2.getText()).isEqualTo(DESCRIPTION);
    }

    @AfterAll
    static void afterAll() {
        browser.quit();
    }

}
