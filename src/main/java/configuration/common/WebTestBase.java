package configuration.common;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import configuration.reporting.ExtentManager;
import configuration.reporting.ExtentTestManager;
import configuration.utilities.ReadProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class WebTestBase {
    //create driver
    public static WebDriver driver;

    //Read Properties
//    public static void readProperties(){
//        ReadProperties.loadProperties("");
//    }
    static Properties readProperties = ReadProperties.loadProperties("src/main/resources/config.properties");
//   String browserStackUsername=readProperties.getProperty("BROWSERSTACK_USERNAME");

    String browserName;
    //credentials for cloud environments
    //Temp email for browserStack:wojerem775@runfons.com    Username:demov_DrIW2X  pwd:demo1234
    //Temp email for SauceLabs:wojerem775@runfons.com    Username:wojerem775  pwd:Demo1234!
    //public static final String BROWSERSTACK_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
//    public static final String BROWSERSTACK_USERNAME = "demov_DrIW2X";
    //public static final String BROWSERSTACK_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
//    public static final String BROWSERSTACK_ACCESS_KEY = "MU5f1trmiq27eJQAWtke";
//    public static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final String BROWSERSTACK_URL = "https://" + readProperties.getProperty("BROWSERSTACK_USERNAME") + ":" + readProperties.getProperty("BROWSERSTACK_ACCESS_KEY") + "@hub-cloud.browserstack.com/wd/hub";


    public static final String SAUCELABS_USERNAME = "wojerem775";
    public static final String SAUCELABS_ACCESS_KEY = "4b51f266-9f50-4ffa-9e30-a89c5fd24614";
    public static final String SAUCELABS_URL = "https://" + SAUCELABS_USERNAME + ":" + SAUCELABS_ACCESS_KEY + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";
//    URL url = new URL("https://wojerem775:4b51f266-9f50-4ffa-9e30-a89c5fd24614@ondemand.us-west-1.saucelabs.com:443/wd/hub");

//    String browserName="chrome";
//    String url="https://www.amazon.com/";

    //    static String browserName;
//    HomePageBeforeMethod homePageBeforeMethod;//not good

    /**
     * **************************************************
     * ********** Start Of Reporting Utilities **********
     * **************************************************
     * **************************************************
     */
    //ExtentReport
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) throws Exception {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }
        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            //logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
            // logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
            //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
            String screenshotPath = captureScreenShotWithPath(driver, result.getName());
            //To add it in the extent report
            //   logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));

//            if (result.getStatus() == ITestResult.FAILURE) {
//                captureScreenShotWithPath(driver, result.getName());
//                logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
//            }

        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();

        // driver.close();
        //driver.quit();
        // ending test
        //endTest(logger) : It ends the current test and prepares to create HTML report
        extent.endTest(logger);
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }


    public static String convertToString(String st) {
        String splitString = "";
        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
        return splitString;
    }

    /**
     * **************************************************
     * ********** End Of Reporting Utilities **********
     * **************************************************
     * **************************************************
     */


    @BeforeTest
    public void setUpAutomation() {
        System.out.println("********** Automation Started ************");
//      homePageBeforeMethod = PageFactory.initElements(driver, HomePageBeforeMethod.class);//not good because we need to do this for every test class
    }


    //@AfterTest(alwaysRun = true)
    @AfterMethod
    public void tearDownAutomation() {
//        driver.close();//closing window during run time
        if (driver != null) {
            driver.quit();//it will close all the windows
        }

        System.out.println("********** Automation Ended ************");
    }


//    /**
//     * This method will accept url based on environment from command line during the execution
//     *
//     * @param useCloudEnv
//     * @param cloudEnvName
//     * @param os
//     * @param osVersion
//     * @param browserName
//     * @param browserVersion
//     * @throws MalformedURLException
//     */
//    //@BeforeTest
//    @Parameters({"useCloudEnv", "cloudEnvName", "os", "osVersion", "browserName", "browserVersion"})
//    @BeforeMethod
//    public void setUp(@Optional("false") boolean useCloudEnv, @Optional("sauceLabs") String cloudEnvName, @Optional("OS X") String os, @Optional("Big Sure") String osVersion, @Optional("firefox") String browserName, @Optional("100") String browserVersion) throws MalformedURLException {
//        if (useCloudEnv) {
//            if (cloudEnvName.equalsIgnoreCase("browserStack") || cloudEnvName.equalsIgnoreCase("sauceLabs")) {
//                getCloudDriver(cloudEnvName, os, osVersion, browserName, browserVersion);
//            }
//        } else {
//            getLocalDriver(os, browserName);
//        }
////        log("Browser: " + browserName);
////        log("url: " + url);
//        printLog("Browser: " + browserName);
//        printLog("url: " + ReadSystemProperties.getEnvUrl());
////        System.out.println("Hello");
//        driver.get(ReadSystemProperties.getEnvUrl());
//        driver.manage().window().maximize();
////        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
////        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
////        driver.manage().deleteAllCookies();
//    }

    @Parameters({"useCloudEnv", "cloudEnvName", "os", "osVersion", "browserName", "browserVersion", "url"})
    @BeforeMethod
    public void setUp(@Optional("false") boolean useCloudEnv, @Optional("sauceLabs") String cloudEnvName, @Optional("OS X") String os, @Optional("Big Sure") String osVersion, @Optional("firefox") String browserName, @Optional("100") String browserVersion, @Optional("https://www.google.com") String url) throws MalformedURLException {
        if (useCloudEnv) {
            if (cloudEnvName.equalsIgnoreCase("browserStack") || cloudEnvName.equalsIgnoreCase("sauceLabs")) {
                getCloudDriver(cloudEnvName, os, osVersion, browserName, browserVersion);
            }
        } else {
            getLocalDriver(os, browserName);
        }
//        log("Browser: " + browserName);
//        log("url: " + url);
        printLog("Browser: " + browserName);
        printLog("url: " + url);
//        System.out.println("Hello");
        driver.get(url);
        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
//        driver.manage().deleteAllCookies();
    }

    public static void printLog(final String message) {
        Reporter.log(message, true);
    }


    public WebDriver getLocalDriver(String os, String browserName) {

        if (browserName.equalsIgnoreCase("chrome")) {
            if (os.equalsIgnoreCase("OS X")) {
//              System.setProperty("webdriver.chrome.driver", "../TDD_WebAutomationFrameworkSingleModule/browserDriver/chromedriver");//sub folder for mac//mac no .exe
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDriver/chromedriver");//sub folder for mac//mac no .exe
            } else if (os.equalsIgnoreCase("windows")) {
//              System.setProperty("webdriver.chrome.driver", "../TDD_WebAutomationFrameworkSingleModule/browserDriver/chromedriver.exe");//sub folder for windows
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDriver/chromedriver.exe");//sub folder for mac//mac no .exe
            }
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("chrome-options")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito");
            options.addArguments("--start-maximized");
            ChromeOptions capability = new ChromeOptions();
            capability.setCapability(ChromeOptions.CAPABILITY, options);

            if (os.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDriver/chromedriver");//sub folder for mac
            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDriver/chromedriver.exe");//sub folder for windows
            }
            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            // options.setHeadless(true);//to not display all the favorite tabs...
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--private");
            FirefoxOptions capability = new FirefoxOptions();
            capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            if (os.equalsIgnoreCase("OS X")) {
//              System.setProperty("webdriver.gecko.driver", "../TDD_WebAutomationFrameworkSingleModule/browserDriver/geckodriver");//sub folder for mac
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/browserDriver/geckodriver");//sub folder for mac
            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/browserDriver/geckodriver.exe");//sub folder for windows
            }
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("ie")) {//no mac
            if (os.equalsIgnoreCase("windows")) {
//              System.setProperty("webdriver.ie.driver", "../TDD_WebAutomationFrameworkSingleModule/browserDriver/IEDriverServer.exe");//sub folder for windows
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/browserDriver/IEDriverServer.exe");//sub folder for windows
            }
            driver = new InternetExplorerDriver();

        } else if (browserName.equalsIgnoreCase("safari")) {//no windows
            if (os.equalsIgnoreCase("OS X")) {
//              System.setProperty("webdriver.safari.driver", "../TDD_WebAutomationFrameworkSingleModule/browserDriver/safaridriver");//sub folder for mac
                System.setProperty("webdriver.safari.driver", System.getProperty("user.dir") + "/browserDriver/safaridriver");//sub folder for mac
            }
            driver = new SafariDriver();
        }
        return driver;
    }

    //https://automate.browserstack.com/dashboard/v2/quick-start/integrate-test-suite-step#integrate-your-test-suite-with-browserstack
    //https://app.saucelabs.com/platform-configurator
    public WebDriver getCloudDriver(String envName, String os, String osVersion, String browserName, String browserVersion) throws MalformedURLException {
        // Add the following capabilities to your test script
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion", browserVersion);
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("os", os);
        options.put("osVersion", osVersion);

        if (envName.equalsIgnoreCase("browserStack")) {
//            capabilities.setCapability("resolution", "1024x786");
            capabilities.setCapability("bstack:options", options);
            driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), capabilities);


        } else if (envName.equalsIgnoreCase("sauceLabs")) {
            capabilities.setCapability("sauce:options", options);
//            URL url = new URL("https://wojerem775:4b51f266-9f50-4ffa-9e30-a89c5fd24614@ondemand.us-west-1.saucelabs.com:443/wd/hub");
            driver = new RemoteWebDriver(new URL(SAUCELABS_URL), capabilities);

        }
        return driver;
    }

    public static String captureScreenShotWithPath(WebDriver driver, String screenShotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String fileName = System.getProperty("user.dir") + "/screenShots/" + screenShotName + "_" + dateName + ".png";
        try {
            FileUtils.copyFile(file, new File(fileName));
            printLog("ScreenShot Captured");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            printLog("Exception while taking screenShot " + e.getMessage());
        }
        return fileName;
    }

    public void waitFor(int seconds) throws InterruptedException {
        Thread.sleep(1000 * seconds);
    }
}
