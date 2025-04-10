package Com.jio.util;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestResultListener implements ITestListener {
    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;

    @Override
    public void onTestStart(ITestResult result) {
        Allure.step("Starting test: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        // Test suite start
    }

    @Override
    public void onFinish(ITestContext context) {
        String resultSummary = String.format(
                "Test Results Summary:\nPassed: %d\nFailed: %d\nSkipped: %d",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size()
        );

        Allure.addAttachment("Test Execution Summary", resultSummary);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passCount++;
        updateTestMetrics();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failCount++;
        updateTestMetrics();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipCount++;
        updateTestMetrics();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Handle test failed within success percentage
    }

    private void updateTestMetrics() {
        String metrics = String.format(
                "Current Test Metrics:\nPassed: %d\nFailed: %d\nSkipped: %d",
                passCount, failCount, skipCount
        );
        Allure.addAttachment("Test Metrics", metrics);
    }
}
