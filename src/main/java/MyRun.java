import io.appium.java_client.android.AndroidDriver;
import io.netty.util.internal.StringUtil;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyRun {

    private static AndroidDriver driver;
    private static String LOG_PATTERN = "NETWORK_LOG";
    private static final String deviceName = "Nexus 5";
    private static final String platformVersion = "6.0.1";

    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/Apps/MMT/");
        File app = new File(appDir, "makemytrip.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());

        capabilities.setCapability("appPackage", "com.makemytrip");
        capabilities.setCapability("appActivity", "com.mmt.travel.app.home.ui.SplashActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        Thread.sleep(5000);
        driver.findElementById("com.makemytrip:id/skip_onboarding").click();
        Thread.sleep(5000);
        driver.findElementById("com.makemytrip:id/ivIcon").click();
        Thread.sleep(2000);
        driver.findElementById("com.makemytrip:id/searchFlights").click();
        Thread.sleep(5000);
        printLogEntries(logEntries);
        driver.quit();
    }


    private static void printLogEntries(List<LogEntry> logEntries) {
        System.out.println("######################Logging Start#####################");
        if (logEntries == null || logEntries.size() <= 0) {
            System.out.println("Logs are either null or empty");
            return;
        }

        for (LogEntry log : logEntries) {
            String message = log.getMessage();
            if (!StringUtil.isNullOrEmpty(message) && message.contains(LOG_PATTERN)) {
                System.out.println(message);
            }
        }
        System.out.println("#######################Logging Done####################");
    }
}