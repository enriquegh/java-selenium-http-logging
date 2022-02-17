import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
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

//        Enable these to proxy all traffic
//        System.setProperty("http.proxyHost", "localhost");
//        System.setProperty("https.proxyHost", "localhost");
//        System.setProperty("http.proxyPort", "8889");
//        System.setProperty("https.proxyPort", "8889");

        try {


            HTTPFactory factory = new HTTPFactory();
            AppiumCommandExecutor executor = new AppiumCommandExecutor(MobileCommand.commandRepository, new URL(SAUCE_URL), factory);

            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "iOS");
            //TODO: Change to your Sauce Storage ID
            caps.setCapability("appium:app", "storage:81438268-f41e-429c-bcb4-c8f0047c13e7");
            caps.setCapability("appium:deviceName", "iPhone 13 Simulator");
            caps.setCapability("appium:platformVersion", "15.0");
            MutableCapabilities sauceOptions = new MutableCapabilities();
            sauceOptions.setCapability("appiumVersion", "1.22.0");
            caps.setCapability("sauce:options", sauceOptions);

            IOSDriver driver = new IOSDriver<MobileElement>(executor, caps);


            WebElement user = driver.findElement(MobileBy.AccessibilityId("test-Username"));
            user.sendKeys("test-user");

            MobileElement loginBtn = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-LOGIN"));

            TapOptions tapOptions = new TapOptions();
            tapOptions.withElement(ElementOption.element(loginBtn));

            TouchAction action = new TouchAction(driver);
            action.tap(tapOptions).perform();

            driver.getPageSource();

            driver.quit();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

    }


}
