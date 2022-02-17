import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
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
            //TODO: Change to your Sauce Storage ID
            caps.setCapability("app", "storage:81438268-f41e-429c-bcb4-c8f0047c13e7");
            caps.setCapability("deviceName", "iPhone 13 Simulator");
            caps.setCapability("platformVersion", "15.0");
            caps.setCapability("appiumVersion", "1.22.0");

            IOSDriver<MobileElement> driver = new IOSDriver<MobileElement>(new URL(SAUCE_URL), caps);


            WebElement user = driver.findElement(MobileBy.AccessibilityId("test-Username"));
            user.sendKeys("test-user");

            MobileElement loginBtn = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-LOGIN"));

            TouchAction action = new TouchAction(driver);
            action.tap(loginBtn).perform();

            driver.getPageSource();

            driver.quit();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

    }


}
