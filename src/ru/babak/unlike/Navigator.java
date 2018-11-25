package ru.babak.unlike;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

/**
 * Этот класс будет браузером
 */
public class Navigator {

    private static final Object lock = new Object();

    private static WebDriverWait wait;
    private static JavascriptExecutor js;
    private static WebDriver driver;

    /**
     * Открываем окно и логинимся в ВК
     */
    public Navigator() {
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        String baseUrl = "https://vk.com/";
        driver.get( baseUrl);

        wait = new WebDriverWait( driver, 6000);
        final WebElement top_logout_link = wait.until( ExpectedConditions.presenceOfElementLocated(By.id("top_logout_link")) );

    }

    /**
     * Этот метод снимает лайки с одной ВК-сущности
     */
    public static void unlike(ArrayList<String> links, vkObj obj) throws InterruptedException {

        Integer counter = 0;
        for (String link : links) {

            driver.get( link.toString() );
            wait.until( webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            if ( driver.findElements(By.cssSelector("div.like_btns > a.like_btn.like._like.active > div.like_button_icon")).size() != 0) {

                WebElement like = driver.findElement( By.cssSelector( "div.like_btns > a.like_btn.like._like.active > div.like_button_icon") );
                js.executeScript("arguments[0].scrollIntoView();window.scrollBy(0, -40);", like);
                like.click();

                System.out.println(counter.toString() + " " + link.toString() + " click;");
                synchronized (lock) {
                    lock.wait(144);
                }

                counter = ++counter;
                if (counter > 2) {
                    //Это для отладки
//                    return;
//                    System.exit(0);
                }

            } else System.out.println(counter.toString() + " " + link.toString() + " skip;");

        }
    }
}
