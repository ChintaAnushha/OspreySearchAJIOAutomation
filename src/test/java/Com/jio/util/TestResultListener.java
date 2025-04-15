package Com.jio.util;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class TestResultListener implements ITestListener {
    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;
    private long startTime;
    private Map<String, String> testStatusMap = new HashMap<>();

    @Override
    public void onTestStart(ITestResult result) {
        //Allure.step("Starting test: " + result.getName());
        String testName = result.getMethod().getMethodName();
        Allure.epic("Osprey Search API Testing");
        Allure.feature(result.getTestClass().getName());
        Allure.step("Starting test: " + testName);
    }

    @Override
    public void onStart(ITestContext context) {
        startTime = System.currentTimeMillis();
        Allure.epic("Osprey Search API Testing");
        Allure.label("Test Suite", context.getName());
      //  Allure.addAttachment("Test Suite", "Starting test suite: " + context.getName());
        // Test suite start
    }

    @Override
    public void onFinish(ITestContext context) {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        int totalTests = passCount + failCount + skipCount;
        double successRate = totalTests > 0 ? (double) passCount / totalTests * 100 : 0; //(double) passCount / (passCount + failCount + skipCount) * 100;

        String resultSummary = String.format(
                "Test Execution Summary\n" +
                        "====================\n" +
                        "Total Tests: %d\n" +
                        "Passed: %d\n" +
                        "Failed: %d\n" +
                        "Skipped: %d\n" +
                        "Success Rate: %.2f%%\n" +
                        "Duration: %d seconds\n" +
                        "====================",
                (passCount + failCount + skipCount),
                passCount,
                failCount,
                skipCount,
                successRate,
                duration
        );

        Allure.description(resultSummary);
        Allure.addAttachment("Test Results Summary", resultSummary);
//        String resultSummary = String.format(
//                "Test Results Summary:\nPassed: %d\nFailed: %d\nSkipped: %d",
//                context.getPassedTests().size(),
//                context.getFailedTests().size(),
//                context.getSkippedTests().size()
//        );
//
//        Allure.addAttachment("Test Execution Summary", resultSummary);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
//        passCount++;
//        updateTestMetrics();
        String testName = result.getMethod().getMethodName();
        testStatusMap.put(testName, "PASSED");
        updateTestMetrics();
        addTestDetails(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failCount++;
       // updateTestMetrics();
        String testName = result.getMethod().getMethodName();
        testStatusMap.put(testName, "FAILED");
        updateTestMetrics();
        addTestDetails(result, "FAILED");

        // Add failure details
        String errorMessage = result.getThrowable() != null ?
                result.getThrowable().getMessage() : "No error message available";
        Allure.addAttachment("Failure Details", errorMessage);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipCount++;
       // updateTestMetrics();
        String testName = result.getMethod().getMethodName();
        testStatusMap.put(testName, "SKIPPED");
        updateTestMetrics();
        addTestDetails(result, "SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Handle test failed within success percentage
        failCount++;
        updateTestMetrics();
    }

    private void addTestDetails(ITestResult result, String status) {
        String testName = result.getMethod().getMethodName();
        String testClass = result.getTestClass().getName();
        long duration = result.getEndMillis() - result.getStartMillis();

        String details = String.format(
                "Test Details\n" +
                        "============\n" +
                        "Name: %s\n" +
                        "Class: %s\n" +
                        "Status: %s\n" +
                        "Duration: %d ms\n" +
                        "============",
                testName, testClass, status, duration
        );

        Allure.addAttachment("Test Execution Details", details);
    }

    private void updateTestMetrics() {
        String metrics = String.format(
                "Current Test Metrics\n" +
                        "==================\n" +
                        "Passed: %d\n" +
                        "Failed: %d\n" +
                        "Skipped: %d\n" +
                        "Total: %d\n" +
                        "==================",
                passCount, failCount, skipCount,
                (passCount + failCount + skipCount)
        );
        Allure.addAttachment("Test Metrics", metrics);
    }
}
