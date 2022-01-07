import io.appium.java_client.AppiumDriver;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;


public class SampleAppiumDriverTest {

    public static final String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
    public static final String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    public static final String SAUCE_URL = String.format("https://%s:%s@ondemand.us-west-1.saucelabs.com/wd/hub",SAUCE_USERNAME,SAUCE_ACCESS_KEY);



    @Test
    public void myTest() throws MalformedURLException {

        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");

//        Enable these to proxy all traffic
//        System.setProperty("http.proxyHost", "localhost");
//        System.setProperty("https.proxyHost", "localhost");
//        System.setProperty("http.proxyPort", "8889");
//        System.setProperty("https.proxyPort", "8889");

        try {

            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "iOS");
            caps.setCapability("browserName", "Safari");
            caps.setCapability("deviceName", "iPhone 13 Simulator");
            caps.setCapability("platformVersion", "15.0");
            caps.setCapability("appiumVersion", "1.22.0");

            AppiumDriver driver = new AppiumDriver(new URL(SAUCE_URL), caps);

            driver.get("https://saucedemo.com");

            WebElement user = driver.findElementById("user-name");
            user.sendKeys("test-user");

            driver.getPageSource();

            driver.quit();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

    }


}
