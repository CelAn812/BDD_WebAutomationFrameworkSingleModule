package configuration.common;

//We commented this entire class and added the code to "WebTestBase"
public class ReportingConfig extends WebTestBase{

//
//    /**
//     * **************************************************
//     * ********** Start Of Reporting Utilities **********
//     * **************************************************
//     * **************************************************
//     */
//    //ExtentReport
//    public static ExtentReports extent;
//    public static ExtentTest logger;
//
//    @BeforeSuite
//    public void extentSetup(ITestContext context) {
//        ExtentManager.setOutputDirectory(context);
//        extent = ExtentManager.getInstance();
//    }
//
//    @BeforeMethod
//    public void startExtent(Method method) {
//        String className = method.getDeclaringClass().getSimpleName();
//        String methodName = method.getName().toLowerCase();
//        ExtentTestManager.startTest(method.getName());
//        ExtentTestManager.getTest().assignCategory(className);
//    }
//
//    protected String getStackTrace(Throwable t) {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        t.printStackTrace(pw);
//        return sw.toString();
//    }
//
//    @AfterMethod
//    public void afterEachTestMethod(ITestResult result) throws Exception {
//        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
//        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
//        for (String group : result.getMethod().getGroups()) {
//            ExtentTestManager.getTest().assignCategory(group);
//        }
//        if (result.getStatus() == 1) {
//            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
//        } else if (result.getStatus() == 2) {
//            //logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
//            // logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
//            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
//            //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
//            String screenshotPath = captureScreenShotWithPath(driver, result.getName());
//            //To add it in the extent report
//            //   logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
//
////            if (result.getStatus() == ITestResult.FAILURE) {
////                captureScreenShotWithPath(driver, result.getName());
////                logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
////            }
//
//        } else if (result.getStatus() == 3) {
//            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
//        }
//        ExtentTestManager.endTest();
//        extent.flush();
//
//        // driver.close();
//        //driver.quit();
//        // ending test
//        //endTest(logger) : It ends the current test and prepares to create HTML report
//        extent.endTest(logger);
//    }
//
//    @AfterSuite
//    public void generateReport() {
//        extent.close();
//    }
//
//    private Date getTime(long millis) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(millis);
//        return calendar.getTime();
//    }
//
//
//    public static String convertToString(String st) {
//        String splitString = "";
//        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
//        return splitString;
//    }
//
//    /**
//     * **************************************************
//     * ********** End Of Reporting Utilities **********
//     * **************************************************
//     * **************************************************
//     */

}
