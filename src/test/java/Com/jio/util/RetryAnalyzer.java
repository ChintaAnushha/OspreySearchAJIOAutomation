package Com.jio.util;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.openqa.selenium.io.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;
    private static final long WAIT_TIME_MS = 20000;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            try {
                retryCount++;
                Allure.step("Retrying test - Attempt " + retryCount);
                Thread.sleep(WAIT_TIME_MS);
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        } else {
            // On final failure, capture and log details
            captureFailureDetails(result);
        }
        return false;
    }

    private void captureFailureDetails(ITestResult result) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String testName = result.getMethod().getMethodName();

        // Log error details
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            Allure.addAttachment("Error Details", throwable.getMessage());
        }

        // Capture API response if available
        Object response = result.getAttribute("apiResponse");
        if (response != null) {
            Allure.addAttachment("Failed API Response", response.toString());
        }

        // Create failure report directory if it doesn't exist
        File failureDir = new File("test-output/failures");
        if (!failureDir.exists()) {
            failureDir.mkdirs();
        }

        // Save detailed failure report
        try {
            String reportPath = String.format("test-output/failures/%s_%s_failure_report.txt", testName, timestamp);
            String reportContent = String.format(
                    "Test: %s\nTimestamp: %s\nError: %s\nResponse: %s",
                    testName,
                    timestamp,
                    throwable != null ? throwable.getMessage() : "No error message",
                    response != null ? response.toString() : "No response available"
            );
            FileUtils.writeStringToFile(new File(reportPath), reportContent, "UTF-8");
            Allure.addAttachment("Failure Report", "text/plain", reportContent);
        } catch (IOException e) {
            Allure.addAttachment("Report Creation Failed", e.getMessage());
        }
    }
}
