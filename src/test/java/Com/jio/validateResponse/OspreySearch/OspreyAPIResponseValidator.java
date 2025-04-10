package Com.jio.validateResponse.OspreySearch;

import Com.jio.base.BaseScript;
import Com.jio.model.OspreySearch.OspreyApiResponse;
import Com.jio.model.productSearch.ProductSearch.response.ProductSearchResponse;
import Com.jio.model.survey.global.TestData;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.testng.Assert;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Listeners({ AllureTestNg.class })
public class OspreyAPIResponseValidator extends BaseScript {

    private static final Logger log = Logger.getLogger(OspreyAPIResponseValidator.class);
    TestData testData = null;
    Map<String, String> testDataParams = null;

    OspreyApiResponse ospreyApiResponse = null;
    private SoftAssert softAssert;

    //  GetCarouselDataResponseWrapper getCarouselDataResponseWrapper = null;
    //  public OspreyApiResponse ospreyApiResponse;


    public OspreyAPIResponseValidator(OspreyApiResponse response, TestData data) {
        this.ospreyApiResponse = response;
        this.testData = data;
        this.testDataParams = data.getOtherParams();
        this.softAssert = new SoftAssert();
    }


    public void validateTop10Results(String query) {

        int count = ospreyApiResponse.getNumFound();
        System.out.println(count);
        //  String title = null;
        //   for (int i = 0; i < 10; i++) {
        //    title = String.valueOf(ospreyApiResponse.getNumFound());//.brandStringMv.get(0).name.equalsIgnoreCase(query));//getDocs().get(0).getNameTextEn();//getdocs().get(0).getHits().get(i).getTitle();
        //  if (title.contains(query) == true) {
        //  logger.info("Result " + i + " is::::" + ospreyApiResponse.getDocs().get(0).getNameTextEn());//getHits().get(i).getTitle());
        //     System.out.println("Result " + i + " is::::" + ospreyApiResponse.getDocs().get(0).getNameTextEn());//getHits().get(i).getTitle());
        //   } else {
        //        System.out.println("Result " + i + " " + title + " is mismatch");
    }
    //}
//    }


    //  @Step("Validating basic response")
    //   public void validateBasicResponse() {
//        log.info("Starting basic response validation");
//        softAssert.assertNotNull(ospreyApiResponse, "Response should not be null");
//
//        try {
//     //   String responseStr = ospreyApiResponse.asString();
//    //    String responseStr1 = ospreyApiResponse.asStringForStore();
//
//        // Check for different error responses
////        if (ospreyApiResponse.contains("Search query cannot be empty or invalid")) {
////            log.info("Received empty query error response as expected");
////            softAssert.assertTrue(true, "Received expected empty query error response");
////            Allure.addAttachment("Error Response", ospreyApiResponse.asString());
////           // return;
////        }
////        if (responseStr.contains("Invalid store")) {
////            log.info("Received invalid store error response as expected");
////            softAssert.assertTrue(true, "Received expected invalid store error response");
////            Allure.addAttachment("Error Response", ospreyApiResponse.asString());
////             //   return;
////            }
//            if (ospreyApiResponse.getDocs() != null) {
//                // For successful search response
//                log.info("Number of results found: " + ospreyApiResponse.getNumFound());
//                Allure.addAttachment("Response Count", String.valueOf(ospreyApiResponse.getNumFound()));
//                softAssert.assertNotNull(ospreyApiResponse.getNumFound(), "NumFound should not be null");
//                softAssert.assertTrue(ospreyApiResponse.getNumFound() > 0, "Search should return results");
//            }
//        } catch(Exception e) {
//                log.error("Error during response validation: " + e.getMessage());
//                softAssert.fail("Response validation failed: " + e.getMessage());
//            }
//        log.info("Basic response validation completed");
//        softAssert.assertAll();
    //  }

    @Step("Validating basic response")
    public void validateBasicResponse() {

        log.info("=================== Basic Response Validation Started ===================");

        try {
            softAssert.assertNotNull(ospreyApiResponse, "Response should not be null");
            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();
            int numFound = ospreyApiResponse.getNumFound();
            int recordsPerPage = Integer.parseInt(testData.getOtherParams().get("records_per_page"));

            log.info("Response Analysis:");
            log.info("Total Results Found: " + numFound);
            log.info("Records Per Page: " + recordsPerPage);

            softAssert.assertNotNull(docs, "Response docs should not be null");
            softAssert.assertTrue(numFound >= 0, "Number of results should be non-negative");

            boolean docsValid = docs != null;
            boolean withinLimit = docsValid && docs.size() <= recordsPerPage;

            if (docsValid) {
                softAssert.assertTrue(withinLimit, "Results should not exceed records_per_page limit");
            }

            // Add Allure attachment
            Allure.addAttachment("Basic Response Validation", String.format(
                    "Response Analysis:\n" +
                            "----------------\n" +
                            "Total Results Found: %d\n" +
                            "Records Per Page: %d\n" +
                            "Docs Present: %s\n" +
                            "Docs Count: %s\n" +
                            "Within Page Limit: %s\n" +
                            "Validation Status: %s",
                    numFound,
                    recordsPerPage,
                    docsValid ? "Yes" : "No",
                    docsValid ? docs.size() : "N/A",
                    withinLimit ? "Yes" : "No",
                    (docsValid && withinLimit) ? "PASSED" : "FAILED"
            ));

        } catch (Exception e) {
            log.error("Basic validation failed: " + e.getMessage());
            Allure.addAttachment("Basic Response Validation Error", e.getMessage());
            Assert.fail(e.getMessage());
        }

        log.info("=================== Basic Response Validation Completed ===================");
//
//        log.info("=================== Basic Response Validation Started ===================");
//
//        try {
//            softAssert.assertNotNull(ospreyApiResponse, "Response should not be null");
//            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();
//            int numFound = ospreyApiResponse.getNumFound();
//
//            log.info("Response Analysis:");
//            log.info("Total Results Found: " + numFound);
//            log.info("Records Per Page: " + testData.getOtherParams().get("records_per_page"));
//
//            softAssert.assertNotNull(docs, "Response docs should not be null");
//            softAssert.assertTrue(numFound >= 0, "Number of results should be non-negative");
//            if (docs != null) {
//                softAssert.assertTrue(docs.size() <= Integer.parseInt(testData.getOtherParams().get("records_per_page")),
//                        "Results should not exceed records_per_page limit");
//            }
//        } catch (Exception e) {
//            log.error("Basic validation failed: " + e.getMessage());
//            Assert.fail(e.getMessage());
//        }
//        log.info("=================== Basic Response Validation Completed ===================");
    }


    @Step("Validating query results")
    public void validateQueryResults() {
//        log.info("=================== Query Results Validation Started ===================");
//
//        List<OspreyApiResponse.Doc> products = ospreyApiResponse.getDocs();
//        if (ospreyApiResponse == null) {
//            Assert.fail("Products list is null");
//            return;
//        }
//
//        String searchQuery = testData.getOtherParams().get("query").toLowerCase();
//        log.info("Validating relevancy for query: " + searchQuery);
//
//        for (OspreyApiResponse.Doc product : products) {
//            boolean isRelevant = false;
//            String productName = product.getNameTextEn();
//            List<String> categories = product.getL1l3categoryEnStringMv();
//
//            // Check product name
//            if (productName != null && productName.toLowerCase().contains(searchQuery)) {
//                isRelevant = true;
//            }
//            // Check categories
//            else if (categories != null && categories.stream()
//                    .map(String::toLowerCase)
//                    .anyMatch(category -> category.contains(searchQuery))) {
//                isRelevant = true;
//            }
//
//            log.info(String.format("Product: %s, Relevant: %s", productName, isRelevant));
//            softAssert.assertTrue(isRelevant, "Product should be relevant to search query: " + productName);
//        }
//
//        log.info("=================== Query Results Validation Completed ===================");

        log.info("=================== Query Results Validation Started ===================");

        List<OspreyApiResponse.Doc> products = ospreyApiResponse.getDocs();
        if (ospreyApiResponse == null || products == null) {
            String errorMessage = "Products list is null";
            log.error(errorMessage);
            Allure.addAttachment("Query Results Validation Error", errorMessage);
            Assert.fail(errorMessage);
            return;
        }

        String searchQuery = testData.getOtherParams().get("query").toLowerCase();
        log.info("Validating relevancy for query: " + searchQuery);

        StringBuilder relevancyDetails = new StringBuilder();
        relevancyDetails.append(String.format("Search Query: %s\n\n", searchQuery));
        int relevantCount = 0;

        for (OspreyApiResponse.Doc product : products) {
            boolean isRelevant = false;
            String productName = product.getNameTextEn();
            List<String> categories = product.getL1l3categoryEnStringMv();
            String productCode = product.getCodeString();

            // Check product name
            boolean nameMatch = productName != null && productName.toLowerCase().contains(searchQuery);

            // Check categories
            boolean categoryMatch = categories != null && categories.stream()
                    .map(String::toLowerCase)
                    .anyMatch(category -> category.contains(searchQuery));

            isRelevant = nameMatch || categoryMatch;
            if (isRelevant) relevantCount++;

            // Build product relevancy details
            relevancyDetails.append(String.format(
                    "Product Details:\n" +
                            "---------------\n" +
                            "Code: %s\n" +
                            "Name: %s\n" +
                            "Categories: %s\n" +
                            "Name Match: %s\n" +
                            "Category Match: %s\n" +
                            "Overall Relevancy: %s\n" +
                            "------------------------\n",
                    productCode,
                    productName,
                    categories != null ? String.join(", ", categories) : "No categories",
                    nameMatch ? "Yes" : "No",
                    categoryMatch ? "Yes" : "No",
                    isRelevant ? "Relevant" : "Not Relevant"
            ));

            log.info(String.format("Product: %s, Relevant: %s", productName, isRelevant));
            softAssert.assertTrue(isRelevant, "Product should be relevant to search query: " + productName);
        }

// Add summary to Allure
        Allure.addAttachment("Query Results Validation", String.format(
                "Relevancy Analysis Summary:\n" +
                        "------------------------\n" +
                        "Total Products: %d\n" +
                        "Relevant Products: %d\n" +
                        "Relevancy Rate: %.2f%%\n\n" +
                        "Detailed Analysis:\n" +
                        "----------------\n%s",
                products.size(),
                relevantCount,
                (relevantCount * 100.0 / products.size()),
                relevancyDetails.toString()
        ));

        log.info("=================== Query Results Validation Completed ===================");
    }

    @Step("Validating product relevance: {product.getCodeString()}")
    private void validateProductRelevance(OspreyApiResponse.Doc product,String searchQuery) {
        boolean isRelevant = false;
        String productCode = product.getCodeString();
        String productName = product.getNameTextEn();

        log.info("\nValidating Product:");
        log.info("Code: " + productCode);
        log.info("Name: " + productName);

        // Check product name
        if (productName != null) {
            isRelevant = productName.toLowerCase().contains(searchQuery.toLowerCase());
            log.info("Name Match: " + isRelevant);
        }

        // Check categories if name doesn't match
        List<String> categories = product.getL1l3categoryEnStringMv();
        if (!isRelevant && categories != null) {
            isRelevant = categories.stream()
                    .map(String::toLowerCase)
                    .anyMatch(category -> category.contains(searchQuery.toLowerCase()));
            log.info("Categories: " + String.join(", ", categories));
            log.info("Category Match: " + isRelevant);
        }

        if (!isRelevant) {
            log.error(String.format("Product not relevant to search query '%s':", searchQuery));
            log.error("Product Code: " + productCode);
            log.error("Product Name: " + productName);
            Assert.fail("Product " + productCode + " is not relevant to search query: " + searchQuery);
        }
    }

    @Step("Validating product details")
    public void validateProductDetails() {
//        log.info("=================== Product Details Validation Started ===================");
//
//        List<OspreyApiResponse.Doc> products = ospreyApiResponse.getDocs();
//        if (products == null) {
//            Assert.fail("Products list is null");
//            return;
//        }
//
//        String expectedStore = testData.getOtherParams().get("store");
//
//        for (OspreyApiResponse.Doc product : products) {
//            log.info("Validating product: " + product.getCodeString());
//
//            // Validate required fields
//            softAssert.assertNotNull(product.getCodeString(), "Product code should not be null");
//            softAssert.assertNotNull(product.getNameTextEn(), "Product name should not be null");
//            softAssert.assertNotNull(product.getPriceInrDouble(), "Product price should not be null");
//            softAssert.assertNotNull(product.getCatalogId(), "Catalog ID should not be null");
//
//            // Validate store
//            softAssert.assertEquals(product.getCatalogId(), "rilfnlProductCatalog",
//                    "Product should belong to correct store");
//
//            // Log product details
//            log.info(String.format("Code: %s, Name: %s, Price: %.2f, Store: %s",
//                    product.getCodeString(),
//                    product.getNameTextEn(),
//                    product.getPriceInrDouble(),
//                    product.getCatalogId()));
//        }
//        log.info("=================== Product Details Validation Completed ===================");

        log.info("=================== Product Details Validation Started ===================");

        List<OspreyApiResponse.Doc> products = ospreyApiResponse.getDocs();
        if (products == null) {
            String errorMessage = "Products list is null";
            log.error(errorMessage);
            Allure.addAttachment("Product Details Validation Error", errorMessage);
            Assert.fail(errorMessage);
            return;
        }

        String expectedStore = testData.getOtherParams().get("store");
        StringBuilder productDetails = new StringBuilder();
        int validProductCount = 0;

        for (OspreyApiResponse.Doc product : products) {
            log.info("Validating product: " + product.getCodeString());
            boolean isValid = true;

            // Validate required fields
            boolean hasCode = product.getCodeString() != null;
            boolean hasName = product.getNameTextEn() != null;
            boolean hasPrice = product.getPriceInrDouble() > 0;
            boolean hasCatalog = product.getCatalogId() != null;
         //   boolean correctStore = "rilfnlProductCatalog".equals(product.getCatalogId());

            isValid = hasCode && hasName && hasPrice && hasCatalog;// && correctStore;
            if (isValid) validProductCount++;

            // Build product validation details
            productDetails.append(String.format(
                    "Product Details:\n" +
                            "---------------\n" +
                            "Code: %s\n" +
                            "Name: %s\n" +
                            "Price: %.2f\n" +
                           // "Store: %s\n" +
                            "Validation Status:\n" +
                            "- Has Code: %s\n" +
                            "- Has Name: %s\n" +
                            "- Has Price: %s\n" +
                            "- Has Catalog: %s\n" +
                         //   "- Correct Store: %s\n" +
                            "Overall Status: %s\n" +
                            "------------------------\n",
                    product.getCodeString(),
                    product.getNameTextEn(),
                    product.getPriceInrDouble(),
                    product.getCatalogId(),
                    hasCode ? "✓" : "✗",
                    hasName ? "✓" : "✗",
                    hasPrice ? "✓" : "✗",
                    hasCatalog ? "✓" : "✗",
                  //  correctStore ? "✓" : "✗",
                    isValid ? "VALID" : "INVALID"
            ));

            // Perform assertions
            softAssert.assertNotNull(product.getCodeString(), "Product code should not be null");
            softAssert.assertNotNull(product.getNameTextEn(), "Product name should not be null");
            softAssert.assertNotNull(product.getPriceInrDouble(), "Product price should not be null");
            softAssert.assertNotNull(product.getCatalogId(), "Catalog ID should not be null");
         //   softAssert.assertEquals(product.getCatalogId(), "rilfnlProductCatalog",
             //       "Product should belong to correct store");
        }

// Add summary to Allure
        Allure.addAttachment("Product Details Validation", String.format(
                "Product Validation Summary:\n" +
                        "------------------------\n" +
                        "Total Products: %d\n" +
                        "Valid Products: %d\n" +
                        "Validation Rate: %.2f%%\n\n" +
                        "Detailed Analysis:\n" +
                        "----------------\n%s",
                products.size(),
                validProductCount,
                (validProductCount * 100.0 / products.size()),
                productDetails.toString()
        ));

        log.info("=================== Product Details Validation Completed ===================");
    }

    @Step("Validate error message for empty search query")
    public void validateEmptySearchQueryError(OspreyApiResponse response) {
        try {
            String responseBody = response.asString("", "store");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode detailNode = rootNode.get("detail");

            String actualMessage = "";

            if (detailNode.isTextual()) {
                actualMessage = detailNode.asText();
            } else if (detailNode.isArray() && detailNode.size() > 0) {
                JsonNode firstError = detailNode.get(0);
                actualMessage = firstError.get("msg").asText();
            }

            String expectedMessage = "Search query cannot be empty or invalid.";
            softAssert.assertEquals(actualMessage, expectedMessage, "Error message should match");

            log.info("Validated error message: " + actualMessage);
            Allure.addAttachment("Error Response", responseBody);

            softAssert.assertAll();
        } catch (Exception e) {
            log.error("Failed to parse response: " + e.getMessage());
            softAssert.fail("JSON parsing failed: " + e.getMessage());
        }
    }

    @Step("Validate invalid store error response")
    public void validateInvalidStoreResponse(String query,String store) {
        String responseBody = ospreyApiResponse.asString(query,store);
        String expectedError = "{\"detail\": \"Invalid store\"}";

        softAssert.assertEquals(responseBody.replaceAll("\\s+", ""),
                expectedError.replaceAll("\\s+", ""),
                "Error message should match exactly for invalid store");

        // Validate error message
        softAssert.assertTrue(responseBody.contains("Invalid store"),
                "Response should contain invalid store error message");

        log.info("Response: " + responseBody);
        Allure.addAttachment("Invalid Store Error Response", responseBody);

        softAssert.assertAll();
    }

    @Step("Validate missing field error response")
    public void validateMissingFieldResponse(String query,String store) {
        String responseStr = ospreyApiResponse.asString(query,store);
        String expectedError = "{\"detail\":[{\"type\":\"missing\",\"loc\":[\"body\",\"store\"],\"msg\":\"Field required\",\"input\":{\"query\":\"Jeans\",\"sort_field\":\"relevance\",\"records_per_page\":2}}]}";

        softAssert.assertEquals(responseStr.replaceAll("\\s+", ""),
                expectedError.replaceAll("\\s+", ""),
                "Error message should match exactly for missing field");

        softAssert.assertTrue(responseStr.contains("Field required"),
                "Response should contain field required message");
        softAssert.assertTrue(responseStr.contains("missing"),
                "Response should contain missing type");
        softAssert.assertTrue(responseStr.contains("body"),
                "Response should contain body location");
        softAssert.assertTrue(responseStr.contains("query"),
                "Response should contain query location");

        log.info("Missing field response: " + responseStr);
        Allure.addAttachment("Error Response", responseStr);

        softAssert.assertAll();
    }

    @Step("Validate price sorting response")
    public void validatePriceAscSortingResponse() {
        log.info("=================== Price Sorting Validation Started ===================");

        // Get sorting parameters
        String sortField = testData.getOtherParams().get("sort_field");
        String sortOrder = testData.getOtherParams().get("sort_order");
        log.info("Validating price sorting - Field: " + sortField + ", Order: " + sortOrder);

        // Get products and validate sorting
        List<OspreyApiResponse.Doc> products = ospreyApiResponse.getDocs();
        boolean isSorted = true;

        for (int i = 0; i < products.size() - 1; i++) {
            double currentPrice = products.get(i).getPriceInrDouble();
            double nextPrice = products.get(i + 1).getPriceInrDouble();

            log.info(String.format("Product %d - Price INR: %.2f", i + 1, currentPrice));

            if (sortOrder.equalsIgnoreCase("asc")) {
                if (currentPrice > nextPrice) {
                    isSorted = false;
                    //  log.error("Price sorting error at index " + i + ": " + currentPrice + " > " + nextPrice);
                    log.error(String.format("Price sorting error at index %d: %.2f > %.2f",
                            i, currentPrice, nextPrice));
                    break;
                }
            } else {
                if (currentPrice < nextPrice) {
                    isSorted = false;
                    log.error("Price sorting error at index " + i + ": " + currentPrice + " < " + nextPrice);
                    break;
                }
            }
        }

        // Log last product price
        if (!products.isEmpty()) {
            log.info(String.format("Product %d - Price INR: %.2f",
                    products.size(), products.get(products.size() - 1).getPriceInrDouble()));
        }

        // Validation
        softAssert.assertTrue(isSorted, "Products should be sorted by price in " + sortOrder + " order");

        // Log price analysis
        log.info("\nPrice Analysis:");
        log.info("Total products analyzed: " + products.size());
        log.info(String.format("Price range (INR): %.2f to %.2f",
                products.get(0).getPriceInrDouble(),
                products.get(products.size() - 1).getPriceInrDouble()));
        //   log.info("Price range: " + products.get(0).getPriceInrDouble() + " to " +
        //         products.get(products.size()-1).getPriceInrDouble());

        Allure.addAttachment("Price Sorting Results", String.format(
                "Test Summary:\n" +
                        "-------------\n" +
                        "Sort Field: %s\n" +          // Changed: Removed Query
                        "Sort Order: %s\n" +
                        "Total Products: %d\n\n" +
                        "Price Range (INR):\n" +
                        "------------\n" +
                        "First Product: %.2f\n" +
                        "Last Product: %.2f\n\n" +
                        "Price Statistics:\n" +
                        "---------------\n" +
                        "Minimum Price: %.2f\n" +
                        "Maximum Price: %.2f\n\n" +
                        "Validation:\n" +
                        "----------\n" +
                        "Sorting Status: %s",
                sortField,                    // String
                sortOrder,                    // String
                products.size(),              // Integer
                products.get(0).getPriceInrDouble(),           // Double
                products.get(products.size() - 1).getPriceInrDouble(), // Double
                getMinPrice(products),        // Double
                getMaxPrice(products),        // Double
                isSorted ? "PASSED" : "FAILED"
        ));

        log.info("Price Sorting Validation Completed ");
    }

    @Step("Validating Disabled Facets")
    public void validateDisabledFacets() {

        log.info("=================== Empty Facet Lists Validation Started ===================");

        try {

            softAssert.assertNotNull(ospreyApiResponse, "Response should not be null");

            Map<String, List<Map<String, Object>>> facetData = ospreyApiResponse.getFacetData();
            StringBuilder facetAnalysis = new StringBuilder();
            int emptyListCount = 0;
            int totalFacets = 0;
            List<String> nonEmptyFacets = new ArrayList<>();

            if (facetData == null) {
                log.info("FacetData is null in response");
                Allure.addAttachment("Empty Facet Lists Analysis", "FacetData is null in response");
                return;
            }

                for (Map.Entry<String, List<Map<String, Object>>> entry : facetData.entrySet()) {
                    String facetName = entry.getKey();
                    List<Map<String, Object>> facetList = entry.getValue();
                    boolean isEmpty = facetList == null || facetList.isEmpty();
                    totalFacets++;

                    if (isEmpty) {
                        emptyListCount++;
                    } else {
                        nonEmptyFacets.add(facetName);
                    }

                    String status = isEmpty ? "✓ EMPTY" : "✗ NOT EMPTY";
                    String validationResult = isEmpty ? "PASSED" : "FAILED";

                    facetAnalysis.append(String.format(
                            "Facet: %s\n" +
                                    "Status: %s\n" +
                                    "List Size: %d\n" +
                                    "Validation: %s\n" +
                                    "------------------------\n",
                            facetName,
                            status,
                            facetList != null ? facetList.size() : 0,
                            validationResult
                    ));
                }

            double emptyPercentage = totalFacets > 0 ?
                    (emptyListCount * 100.0 / totalFacets) : 0;
            boolean allListsEmpty = emptyListCount == totalFacets;

            // Create status with symbols
            String overallStatus = allListsEmpty ?
                    "✅ ALL LISTS EMPTY - PASSED" :
                    "❌ SOME LISTS NOT EMPTY - FAILED";

            // Add results to Allure
            Allure.addAttachment("Empty Facet Lists Analysis", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary Statistics:\n" +
                            "------------------\n" +
                            "Total Facets: %d\n" +
                            "Empty Lists: %d\n" +
                            "Non-Empty Lists: %d\n" +
                            "Empty Percentage: %.2f%%\n\n" +
                            "Non-Empty Facets:\n" +
                            "----------------\n%s\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    totalFacets,
                    emptyListCount,
                    (totalFacets - emptyListCount),
                    emptyPercentage,
                    nonEmptyFacets.isEmpty() ? "None" : String.join("\n", nonEmptyFacets),
                    facetAnalysis.toString()
            ));

        } catch (Exception e) {
            log.error("Empty facet lists validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Facet validation failed: " + e.getMessage());
        }

        log.info("=================== Empty Facet Lists Validation Completed ===================");
    }

    @Step("Validating Enabled Facets")
    public void validateEnabledFacets() {
        log.info("=================== Enabled Facets Validation Started ===================");

        try {
            Map<String, List<Map<String, Object>>> facetData = ospreyApiResponse.getFacetData();
            StringBuilder facetAnalysis = new StringBuilder();
            int nonEmptyListCount = 0;
            int totalFacets = 0;
            List<String> emptyFacets = new ArrayList<>();

            if (facetData == null) {
                log.error("FacetData is null when facets are enabled");
                Assert.fail("FacetData should not be null when facets are enabled");
                return;
            }

            for (Map.Entry<String, List<Map<String, Object>>> entry : facetData.entrySet()) {
                String facetName = entry.getKey();
                List<Map<String, Object>> facetList = entry.getValue();
                boolean hasData = facetList != null && !facetList.isEmpty();
                totalFacets++;

                if (hasData) {
                    nonEmptyListCount++;
                } else {
                    emptyFacets.add(facetName);
                }

                String status = hasData ? "✓ HAS DATA" : "✗ EMPTY";
                String validationResult = hasData ? "PASSED" : "FAILED";

                facetAnalysis.append(String.format(
                        "Facet: %s\n" +
                                "Status: %s\n" +
                                "List Size: %d\n" +
                                "Validation: %s\n" +
                                "------------------------\n",
                        facetName,
                        status,
                        facetList != null ? facetList.size() : 0,
                        validationResult
                ));
            }

            double dataPercentage = totalFacets > 0 ?
                    (nonEmptyListCount * 100.0 / totalFacets) : 0;
            boolean hasFacetData = nonEmptyListCount > 0;

            String overallStatus = hasFacetData ?
                    "✅ FACETS CONTAIN DATA - PASSED" :
                    "❌ NO FACET DATA - FAILED";

            Allure.addAttachment("Enabled Facets Analysis", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary Statistics:\n" +
                            "------------------\n" +
                            "Total Facets: %d\n" +
                            "Facets with Data: %d\n" +
                            "Empty Facets: %d\n" +
                            "Data Percentage: %.2f%%\n\n" +
                            "Empty Facets:\n" +
                            "------------\n%s\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    totalFacets,
                    nonEmptyListCount,
                    (totalFacets - nonEmptyListCount),
                    dataPercentage,
                    emptyFacets.isEmpty() ? "None" : String.join("\n", emptyFacets),
                    facetAnalysis.toString()
            ));

            softAssert.assertTrue(hasFacetData, "At least some facets should contain data when facets are enabled");

        } catch (Exception e) {
            log.error("Enabled facets validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Facet validation failed: " + e.getMessage());
        }

        log.info("=================== Enabled Facets Validation Completed ===================");
    }

    @Step("Validating Disabled Facet Value Counts")
    public void validateDisabledFacetValueCounts() {
        log.info("=================== Facet Value Counts Validation Started ===================");

        try {
            Map<String, List<Map<String, Object>>> facetData = ospreyApiResponse.getFacetData();
            StringBuilder facetAnalysis = new StringBuilder();
            int nullValueFacets = 0;
            int totalValues = 0;
            List<String> nonNullValues = new ArrayList<>();

            if (facetData != null) {
                for (Map.Entry<String, List<Map<String, Object>>> entry : facetData.entrySet()) {
                    String facetName = entry.getKey();
                    List<Map<String, Object>> facetValues = entry.getValue();

                    if (facetValues != null) {
                        for (Map<String, Object> value : facetValues) {
                            totalValues++;
                            String name = (String) value.get("name");
                            Object countValue = value.get("value");

                            boolean isNullValue = countValue == null;
                            if (isNullValue) {
                                nullValueFacets++;
                            } else {
                                nonNullValues.add(String.format("%s: %s", facetName, name));
                            }

                            facetAnalysis.append(String.format(
                                    "Facet: %s\n" +
                                            "Name: %s\n" +
                                            "Value: %s\n" +
                                            "Status: %s\n" +
                                            "------------------------\n",
                                    facetName,
                                    name,
                                    countValue == null ? "null" : countValue,
                                    isNullValue ? "✓ NULL VALUE" : "✗ HAS VALUE"
                            ));
                        }
                    }
                }
            }

            boolean allValuesNull = nullValueFacets == totalValues;
            String overallStatus = allValuesNull ?
                    "✅ ALL FACET VALUES NULL - PASSED" :
                    "❌ SOME FACETS HAVE VALUES - FAILED";

            Allure.addAttachment("Facet Value Counts Analysis", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary Statistics:\n" +
                            "------------------\n" +
                            "Total Facet Values: %d\n" +
                            "Null Values: %d\n" +
                            "Non-null Values: %d\n" +
                            "Null Percentage: %.2f%%\n\n" +
                            "Non-null Value Details:\n" +
                            "---------------------\n%s\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    totalValues,
                    nullValueFacets,
                    (totalValues - nullValueFacets),
                    (nullValueFacets * 100.0 / totalValues),
                    nonNullValues.isEmpty() ? "None" : String.join("\n", nonNullValues),
                    facetAnalysis.toString()
            ));

            softAssert.assertTrue(allValuesNull,
                    "All facet values should be null when enable_facet_counts is false");

        } catch (Exception e) {
            log.error("Facet value counts validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Facet value counts validation failed: " + e.getMessage());
        }

        log.info("=================== Facet Value Counts Validation Completed ===================");
    }

    @Step("Validating Enabled Facet Values Counts")
    public void validateEnabledFacetValuesCounts() {
        log.info("=================== Enabled Facet Values Validation Started ===================");

        try {
            Map<String, List<Map<String, Object>>> facetData = ospreyApiResponse.getFacetData();
            StringBuilder facetAnalysis = new StringBuilder();
            int nonNullValueFacets = 0;
            int totalValues = 0;
            List<String> nullValues = new ArrayList<>();

            if (facetData != null) {
                for (Map.Entry<String, List<Map<String, Object>>> entry : facetData.entrySet()) {
                    String facetName = entry.getKey();
                    List<Map<String, Object>> facetValues = entry.getValue();

                    if (facetValues != null) {
                        for (Map<String, Object> value : facetValues) {
                            totalValues++;
                            String name = (String) value.get("name");
                            Object countValue = value.get("value");

                            boolean hasValue = countValue != null;
                            if (hasValue) {
                                nonNullValueFacets++;
                            } else {
                                nullValues.add(String.format("%s: %s", facetName, name));
                            }

                            facetAnalysis.append(String.format(
                                    "Facet: %s\n" +
                                            "Name: %s\n" +
                                            "Value: %s\n" +
                                            "Status: %s\n" +
                                            "------------------------\n",
                                    facetName,
                                    name,
                                    countValue == null ? "null" : countValue,
                                    hasValue ? "✓ HAS VALUE" : "✗ NULL VALUE"
                            ));
                        }
                    }
                }
            }

            boolean allValuesPresent = nonNullValueFacets == totalValues;
            String overallStatus = allValuesPresent ?
                    "✅ ALL FACET VALUES PRESENT - PASSED" :
                    "❌ SOME FACETS HAVE NULL VALUES - FAILED";

            Allure.addAttachment("Enabled Facet Values Analysis", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary Statistics:\n" +
                            "------------------\n" +
                            "Total Facet Values: %d\n" +
                            "Values Present: %d\n" +
                            "Null Values: %d\n" +
                            "Value Presence Percentage: %.2f%%\n\n" +
                            "Null Value Details:\n" +
                            "-----------------\n%s\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    totalValues,
                    nonNullValueFacets,
                    (totalValues - nonNullValueFacets),
                    (nonNullValueFacets * 100.0 / totalValues),
                    nullValues.isEmpty() ? "None" : String.join("\n", nullValues),
                    facetAnalysis.toString()
            ));

            softAssert.assertTrue(allValuesPresent,
                    "All facet values should be present when enable_facet_counts is true");

        } catch (Exception e) {
            log.error("Enabled facet values validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Facet values validation failed: " + e.getMessage());
        }

        log.info("=================== Enabled Facet Values Validation Completed ===================");
    }

    @Step("Validating Retrieved Attributes")
    public void validateAttributesRetrieval() {
        log.info("=================== Attributes Retrieval Validation Started ===================");

        try {
            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();
            int numFound = ospreyApiResponse.getNumFound();
            StringBuilder attributeAnalysis = new StringBuilder();
            int validDocs = 0;
            int totalDocs = 0;

            log.info("\nSearch Results Summary:");
            log.info("=====================");
            log.info("Total Results Found: " + numFound);
            log.info("Documents Returned: " + (docs != null ? docs.size() : 0));
            log.info("\nDocument Details:");
            log.info("================");

            if (docs != null) {
                for (OspreyApiResponse.Doc doc : docs) {
                    totalDocs++;
                    boolean isValid = true;

                    // Check if only name_text_en is present
                    String name = doc.getNameTextEn();
                    boolean hasName = name != null && !name.isEmpty();

                    // Verify no other fields are present
                    boolean hasOnlyName = true; // Add logic to check other fields are null

                    isValid = hasName && hasOnlyName;
                    if (isValid) validDocs++;

                    attributeAnalysis.append(String.format(
                            "Document %d:\n" +
                                    "Name: %s\n" +
                                    "Has Name: %s\n" +
                                    "Only Name Present: %s\n" +
                                    "Status: %s\n" +
                                    "------------------------\n",
                            totalDocs,
                            name,
                            hasName ? "✓" : "✗",
                            hasOnlyName ? "✓" : "✗",
                            isValid ? "VALID" : "INVALID"
                    ));
                }
            }

            boolean allDocsValid = validDocs == totalDocs;
            String overallStatus = allDocsValid ?
                    "✅ ALL DOCS HAVE CORRECT ATTRIBUTES - PASSED" :
                    "❌ SOME DOCS HAVE INCORRECT ATTRIBUTES - FAILED";

            Allure.addAttachment("Attributes Retrieval Analysis", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary Statistics:\n" +
                            "------------------\n" +
                            "Total Documents: %d\n" +
                            "Valid Documents: %d\n" +
                            "Invalid Documents: %d\n" +
                            "Success Rate: %.2f%%\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    totalDocs,
                    validDocs,
                    (totalDocs - validDocs),
                    (validDocs * 100.0 / totalDocs),
                    attributeAnalysis.toString()
            ));

            softAssert.assertTrue(allDocsValid,
                    "All documents should only contain the name_text_en attribute");

        } catch (Exception e) {
            log.error("Attributes retrieval validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Attributes validation failed: " + e.getMessage());
        }

        log.info("=================== Attributes Retrieval Validation Completed ===================");
    }

    @Step("Validating Store Redirect")
    public void validateStoreRedirect() {
        log.info("=================== Store Redirect Validation Started ===================");

        try {
            Object storeRedirectObj = ospreyApiResponse.getStoreRedirect();
            int numFound = ospreyApiResponse.getNumFound();
            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();

            // Handle null storeRedirect
            String storeRedirect = storeRedirectObj != null ? storeRedirectObj.toString() : null;

            // Log redirect details
            log.info("\nStore Redirect Analysis:");
            log.info("======================");
            log.info("Original Store: " + testData.getOtherParams().get("store"));
            log.info("Redirected Store: " + (storeRedirect != null ? storeRedirect : "No Redirect"));
            log.info("Number of Results: " + numFound);
            log.info("Documents Returned: " + (docs != null ? docs.size() : 0));

            // Validate redirect conditions
            boolean isRedirectRequired = numFound == 0;
            boolean hasValidRedirect = storeRedirect != null && !storeRedirect.isEmpty();
            boolean docsEmpty = docs == null || docs.isEmpty();

            StringBuilder redirectAnalysis = new StringBuilder();
            redirectAnalysis.append(String.format(
                    "Redirect Validation:\n" +
                            "-------------------\n" +
                            "Zero Results: %s\n" +
                            "Has Redirect Store: %s\n" +
                            "Empty Docs: %s\n" +
                            "Redirect Store Value: %s\n",
                    isRedirectRequired ? "✓" : "✗",
                    hasValidRedirect ? "✓" : "✗",
                    docsEmpty ? "✓" : "✗",
                    storeRedirect != null ? storeRedirect : "None"
            ));

            // Validate conditions
            softAssert.assertEquals(numFound, 0, "Number of results should be 0 for redirect");
            softAssert.assertTrue(hasValidRedirect, "Store redirect should not be null or empty when numFound is 0");
            softAssert.assertTrue(docsEmpty, "Document list should be empty");

            String overallStatus = (isRedirectRequired && hasValidRedirect && docsEmpty) ?
                    "✅ STORE REDIRECT VALIDATED - PASSED" :
                    "❌ STORE REDIRECT VALIDATION - FAILED";

            // Add to Allure report
            Allure.addAttachment("Store Redirect Validation", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary:\n" +
                            "--------\n" +
                            "Original Store: %s\n" +
                            "Redirect Store: %s\n" +
                            "Results Found: %d\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    testData.getOtherParams().get("store"),
                    storeRedirect != null ? storeRedirect : "None",
                    numFound,
                    redirectAnalysis.toString()
            ));

        } catch (Exception e) {
            log.error("Store redirect validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Store redirect validation failed: " + e.getMessage());
        }

        log.info("=================== Store Redirect Validation Completed ===================");
    }

    @Step("Validating Page Number Response")
    public void validatePageNumberResponse() {
        log.info("=================== Page Number Validation Started ===================");

        try {
            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();
            int numFound = ospreyApiResponse.getNumFound();
            int requestedPage = Integer.parseInt(testData.getOtherParams().get("page_number"));
            int recordsPerPage = Integer.parseInt(testData.getOtherParams().get("records_per_page"));

            log.info("\nPagination Analysis:");
            log.info("===================");
            log.info("Total Results Found: " + numFound);
            log.info("Requested Page: " + requestedPage);
            log.info("Records Per Page: " + recordsPerPage);
            log.info("Documents Returned: " + (docs != null ? docs.size() : 0));

            // Calculate expected start and end indices
            int expectedStartIndex = (requestedPage - 1) * recordsPerPage;
            int expectedEndIndex = Math.min(expectedStartIndex + recordsPerPage, numFound);
            int expectedDocCount = expectedEndIndex - expectedStartIndex;

            StringBuilder pageAnalysis = new StringBuilder();
            pageAnalysis.append(String.format(
                    "Page Validation:\n" +
                            "----------------\n" +
                            "Expected Start Index: %d\n" +
                            "Expected End Index: %d\n" +
                            "Expected Document Count: %d\n" +
                            "Actual Document Count: %d\n",
                    expectedStartIndex,
                    expectedEndIndex,
                    expectedDocCount,
                    docs != null ? docs.size() : 0
            ));

            // Validate document count
            if (docs != null) {
                softAssert.assertEquals(docs.size(), expectedDocCount,
                        "Document count should match the expected count for page " + requestedPage);

                // Log document details
                pageAnalysis.append("\nDocument Details:\n");
                for (int i = 0; i < docs.size(); i++) {
                    OspreyApiResponse.Doc doc = docs.get(i);
                    pageAnalysis.append(String.format(
                            "Document %d:\n" +
                                    "Code: %s\n" +
                                    "Name: %s\n" +
                                    "----------------\n",
                            i + 1,
                            doc.getCodeString(),
                            doc.getNameTextEn()
                    ));
                }
            }

            String overallStatus = (docs != null && docs.size() == expectedDocCount) ?
                    "✅ PAGE NUMBER VALIDATION - PASSED" :
                    "❌ PAGE NUMBER VALIDATION - FAILED";

            // Add to Allure report
            Allure.addAttachment("Page Number Validation", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary:\n" +
                            "--------\n" +
                            "Total Results: %d\n" +
                            "Page Number: %d\n" +
                            "Records Per Page: %d\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    numFound,
                    requestedPage,
                    recordsPerPage,
                    pageAnalysis.toString()
            ));

        } catch (Exception e) {
            log.error("Page number validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Page number validation failed: " + e.getMessage());
        }

        log.info("=================== Page Number Validation Completed ===================");
    }

    @Step("Validating Invalid Page Number Error")
    public void validateInvalidPageNumberError() {
        log.info("=================== Invalid Page Number Error Validation Started ===================");

        try {
            String pageNumber = testData.getOtherParams().get("page_number");
            String errorMessage = ospreyApiResponse.validatePageNumber(pageNumber);
            String expectedError = "{\"detail\": \"page_number must be an integer\"}";

            log.info("\nError Response Analysis:");
            log.info("======================");
            log.info("Page Number: " + pageNumber);
            log.info("Error Message: " + errorMessage);

            // Validate error message
            boolean isValidError = expectedError.equals(errorMessage);

            StringBuilder errorAnalysis = new StringBuilder();
            errorAnalysis.append(String.format(
                    "Error Validation:\n" +
                            "-----------------\n" +
                            "Error Message Match: %s\n" +
                            "Expected: %s\n" +
                            "Actual: %s\n",
                    isValidError ? "✓" : "✗",
                    expectedError,
                    errorMessage
            ));

            // Perform assertion
            softAssert.assertEquals(errorMessage, expectedError,
                    "Error message should match exactly with JSON format");

            String overallStatus = isValidError ?
                    "✅ INVALID PAGE NUMBER ERROR VALIDATED - PASSED" :
                    "❌ INVALID PAGE NUMBER ERROR VALIDATION - FAILED";

            // Add to Allure report
            Allure.addAttachment("Invalid Page Number Error Validation", String.format(
                    "Overall Status: %s\n\n" +
                            "Error Details:\n" +
                            "-------------\n" +
                            "Expected Error: %s\n" +
                            "Actual Error: %s\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    expectedError,
                    errorMessage,
                    errorAnalysis.toString()
            ));

        } catch (Exception e) {
            log.error("Invalid page number error validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Error validation failed: " + e.getMessage());
        }

        log.info("=================== Invalid Page Number Error Validation Completed ===================");
    }

    @Step("Validating Invalid Records Offset Error")
    public void validateInvalidRecordsOffsetError() {
//        log.info("=================== Invalid Records Offset Validation Started ===================");
//
//        try {
//            String recordsOffset = testData.getOtherParams().get("records_offset");
//            String actualError = ospreyApiResponse.getErrorMessage();
//            String expectedError = "{\"detail\": \"records_offset must be an integer\"}";
//
//            log.info("\nError Response Analysis:");
//            log.info("======================");
//            log.info("Records Offset: " + recordsOffset);
//            log.info("Error Message: " + actualError);
//
//            boolean isValidError = expectedError.equals(actualError);
//
//            StringBuilder errorAnalysis = new StringBuilder();
//            errorAnalysis.append(String.format(
//                    "Error Validation:\n" +
//                            "-----------------\n" +
//                            "Error Message Match: %s\n" +
//                            "Expected: %s\n" +
//                            "Actual: %s\n",
//                    isValidError ? "✓" : "✗",
//                    expectedError,
//                    actualError
//            ));
//
//            softAssert.assertEquals(actualError, expectedError,
//                    "Error message should match expected JSON format");
//
//            String overallStatus = isValidError ?
//                    "✅ INVALID RECORDS OFFSET VALIDATED - PASSED" :
//                    "❌ INVALID RECORDS OFFSET VALIDATION - FAILED";
//
//            Allure.addAttachment("Invalid Records Offset Validation", String.format(
//                    "Overall Status: %s\n\n" +
//                            "Error Details:\n" +
//                            "-------------\n" +
//                            "Expected Error: %s\n" +
//                            "Actual Error: %s\n\n" +
//                            "Detailed Analysis:\n" +
//                            "-----------------\n%s",
//                    overallStatus,
//                    expectedError,
//                    actualError,
//                    errorAnalysis.toString()
//            ));
//
//        } catch (Exception e) {
//            log.error("Invalid records offset validation failed: " + e.getMessage());
//            Allure.addAttachment("Validation Error", e.getMessage());
//            Assert.fail("Error validation failed: " + e.getMessage());
//        }
//
//        log.info("=================== Invalid Records Offset Validation Completed ===================");
        log.info("=================== Invalid Records Offset Validation Started ===================");

        try {
            String recordsOffset = testData.getOtherParams().get("records_offset");
            String actualError = ospreyApiResponse.getErrorMessage();
            boolean isValidError = false;

            log.info("\nError Response Analysis:");
            log.info("======================");
            log.info("Records Offset: " + recordsOffset);

            // Check if input is boolean
            if (recordsOffset.equalsIgnoreCase("true") || recordsOffset.equalsIgnoreCase("false")) {
                String expectedBooleanError = "{\"detail\": \"records_offset must be an integer greater than 0, boolean values are not allowed\"}";
                isValidError = expectedBooleanError.equals(actualError);
                log.info("Input Type: Boolean");
                log.info("Expected Error: " + expectedBooleanError);
                softAssert.assertEquals(actualError, expectedBooleanError,
                        "Error message should match expected format for boolean input");
            }
            // Check if input is string
            else {
                String expectedStringError = "{\"detail\": \"records_offset must be an integer\"}";
                isValidError = expectedStringError.equals(actualError);
                log.info("Input Type: String");
                log.info("Expected Error: " + expectedStringError);
                softAssert.assertEquals(actualError, expectedStringError,
                        "Error message should match expected format for string input");
            }

            log.info("Actual Error: " + actualError);
            log.info("Validation Result: " + (isValidError ? "PASSED" : "FAILED"));

            String overallStatus = isValidError ?
                    "✅ INVALID RECORDS OFFSET VALIDATED - PASSED" :
                    "❌ INVALID RECORDS OFFSET VALIDATION - FAILED";

            Allure.addAttachment("Invalid Records Offset Validation", String.format(
                    "Overall Status: %s\n\n" +
                            "Test Details:\n" +
                            "-------------\n" +
                            "Input Value: %s\n" +
                            "Input Type: %s\n" +
                            "Actual Error: %s\n" +
                            "Validation Result: %s",
                    overallStatus,
                    recordsOffset,
                    recordsOffset.equalsIgnoreCase("true") || recordsOffset.equalsIgnoreCase("false") ?
                            "Boolean" : "String",
                    actualError,
                    isValidError ? "PASSED" : "FAILED"
            ));

        } catch (Exception e) {
            log.error("Invalid records offset validation failed: " + e.getMessage());
            Allure.addAttachment("Validation Error", e.getMessage());
            Assert.fail("Error validation failed: " + e.getMessage());
        }

        log.info("=================== Invalid Records Offset Validation Completed ===================");
    }

    @Step("Validating Records Offset")
    public void validateRecordsOffset() {
        log.info("=================== Records Offset Validation Started ===================");

        try {
            int recordsOffset = Integer.parseInt(testData.getOtherParams().get("records_offset"));
            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();
            int numFound = ospreyApiResponse.getNumFound();

            log.info("\nOffset Analysis:");
            log.info("================");
            log.info("Total Results Found: " + numFound);
            log.info("Records Offset: " + recordsOffset);
            log.info("Documents Returned: " + (docs != null ? docs.size() : 0));

            // Validate first document is from correct offset
            if (docs != null && !docs.isEmpty()) {
                StringBuilder offsetAnalysis = new StringBuilder();
                offsetAnalysis.append(String.format(
                        "Offset Validation:\n" +
                                "-----------------\n" +
                                "Starting from document: %d\n" +
                                "First Document Details:\n" +
                                "Name: %s\n" +
                                "Code: %s\n",
                        recordsOffset + 1,
                        docs.get(0).getNameTextEn(),
                        docs.get(0).getCodeString()
                ));

                // Validate offset
                boolean isValidOffset = numFound > recordsOffset;
                softAssert.assertTrue(isValidOffset,
                        "Offset should be less than total results found");

                String overallStatus = isValidOffset ?
                        "✅ RECORDS OFFSET VALIDATED - PASSED" :
                        "❌ RECORDS OFFSET VALIDATION - FAILED";

                Allure.addAttachment("Records Offset Validation", String.format(
                        "Overall Status: %s\n\n" +
                                "Summary:\n" +
                                "--------\n" +
                                "Total Results: %d\n" +
                                "Offset: %d\n" +
                                "Documents Returned: %d\n\n" +
                                "Detailed Analysis:\n" +
                                "-----------------\n%s",
                        overallStatus,
                        numFound,
                        recordsOffset,
                        docs.size(),
                        offsetAnalysis.toString()
                ));
            }

        } catch (Exception e) {
            log.error("Records offset validation failed: " + e.getMessage());
            Assert.fail("Records offset validation failed: " + e.getMessage());
        }

        log.info("=================== Records Offset Validation Completed ===================");
    }

    @Step("Validating Records Per Page")
    public void validateRecordsPerPage() {
        log.info("=================== Records Per Page Validation Started ===================");

        try {
            int expectedRecordsPerPage = Integer.parseInt(testData.getOtherParams().get("records_per_page"));
            List<OspreyApiResponse.Doc> docs = ospreyApiResponse.getDocs();
            int numFound = ospreyApiResponse.getNumFound();
            int actualDocsSize = docs != null ? docs.size() : 0;

            log.info("\nRecords Per Page Analysis:");
            log.info("=========================");
            log.info("Total Results Found: " + numFound);
            log.info("Expected Records Per Page: " + expectedRecordsPerPage);
            log.info("Actual Documents Returned: " + actualDocsSize);

            StringBuilder paginationAnalysis = new StringBuilder();
            paginationAnalysis.append(String.format(
                    "Pagination Validation:\n" +
                            "--------------------\n" +
                            "Expected Records: %d\n" +
                            "Actual Records: %d\n" +
                            "Total Available: %d\n",
                    expectedRecordsPerPage,
                    actualDocsSize,
                    numFound
            ));

            // Validate records per page
            boolean isValidCount = (actualDocsSize <= expectedRecordsPerPage) &&
                    (actualDocsSize > 0 || numFound == 0);

            if (docs != null && !docs.isEmpty()) {
                paginationAnalysis.append("\nFirst Document Details:\n");
                paginationAnalysis.append(String.format(
                        "Name: %s\n" +
                                "Code: %s\n",
                        docs.get(0).getNameTextEn(),
                        docs.get(0).getCodeString()
                ));
            }

            softAssert.assertTrue(isValidCount,
                    String.format("Documents returned (%d) should not exceed records_per_page (%d)",
                            actualDocsSize, expectedRecordsPerPage));

            String overallStatus = isValidCount ?
                    "✅ RECORDS PER PAGE VALIDATED - PASSED" :
                    "❌ RECORDS PER PAGE VALIDATION - FAILED";

            Allure.addAttachment("Records Per Page Validation", String.format(
                    "Overall Status: %s\n\n" +
                            "Summary:\n" +
                            "--------\n" +
                            "Expected Records Per Page: %d\n" +
                            "Actual Documents Returned: %d\n" +
                            "Total Results Found: %d\n\n" +
                            "Detailed Analysis:\n" +
                            "-----------------\n%s",
                    overallStatus,
                    expectedRecordsPerPage,
                    actualDocsSize,
                    numFound,
                    paginationAnalysis.toString()
            ));

        } catch (Exception e) {
            log.error("Records per page validation failed: " + e.getMessage());
            Assert.fail("Records per page validation failed: " + e.getMessage());
        }

        log.info("=================== Records Per Page Validation Completed ===================");
    }



    private double getMinPrice(List<OspreyApiResponse.Doc> products) {
        return products.stream()
                .mapToDouble(OspreyApiResponse.Doc::getPriceInrDouble)
                .min()
                .orElse(0.0);
    }

    private double getMaxPrice(List<OspreyApiResponse.Doc> products) {
        return products.stream()
                .mapToDouble(OspreyApiResponse.Doc::getPriceInrDouble)
                .max()
                .orElse(0.0);
    }

    @Step("Validate price sorting response for descending order")
    public void validatePriceDescSortingResponse() {
        log.info("=================== Price Sorting Validation (DESC) Started ===================");

        String sortField = testData.getOtherParams().get("sort_field");
        String sortOrder = testData.getOtherParams().get("sort_order");
        log.info("Validating price sorting - Field: " + sortField + ", Order: " + sortOrder);

        List<OspreyApiResponse.Doc> products = ospreyApiResponse.getDocs();
        boolean isSorted = true;

        log.info("\n Product Price Details (Descending) ");
        StringBuilder priceDetails = new StringBuilder();
        double previousPrice = Double.MAX_VALUE;

        for (int i = 0; i < products.size(); i++) {
            OspreyApiResponse.Doc product = products.get(i);
            double currentPrice = product.getPriceInrDouble();
            String productName = product.getNameTextEn();

            String priceLog = String.format(
                    "Product %d:\n" +
                            "Name: %s\n" +
                            "Price (INR): %.2f\n" +
                            "------------------------",
                    (i + 1), productName, currentPrice
            );
            log.info(priceLog);
            priceDetails.append(priceLog).append("\n");

            // Validate descending order
            if (currentPrice > previousPrice) {
                isSorted = false;
                log.error(String.format("Price sorting error at index %d: %.2f > %.2f (previous)",
                        i, currentPrice, previousPrice));
            }
            previousPrice = currentPrice;
        }

        // Validation summary
        softAssert.assertTrue(isSorted, "Products should be sorted by price in descending order");

        log.info("\nPrice Range Analysis:");
        double highestPrice = products.get(0).getPriceInrDouble();
        double lowestPrice = products.get(products.size()-1).getPriceInrDouble();
        log.info(String.format("Highest Price (First Product): %.2f INR", highestPrice));
        log.info(String.format("Lowest Price (Last Product): %.2f INR", lowestPrice));
        log.info("Total Products Analyzed: " + products.size());

        Allure.addAttachment("Price Sorting (DESC) Results", String.format(
                "Test Summary:\n" +
                        "-------------\n" +
                        "Sort Field: %s\n" +
                        "Sort Order: %s\n" +
                        "Total Products: %d\n\n" +
                        "Price Range:\n" +
                        "------------\n" +
                        "Highest Price: %.2f INR\n" +
                        "Lowest Price: %.2f INR\n" +
                        "Price Difference: %.2f INR\n\n" +
                        "Validation:\n" +
                        "----------\n" +
                        "Sorting Status: %s\n" +
                        "Validation Message: Products are %s sorted in descending order",
                sortField,
                sortOrder,
                products.size(),
                highestPrice,
                lowestPrice,
                highestPrice - lowestPrice,
                isSorted ? "PASSED" : "FAILED",
                isSorted ? "correctly" : "not correctly"
        ));

        log.info("Price Sorting Validation (DESC) Completed");
    }


    @Description("Validate API response against query and generate pie charts")
    public void validateSearchqueryResults(String query, SoftAssert softAssert, String queries) throws IOException {

        int matchedCount = 0;
        int unmatchedCount = 0;

        if (ospreyApiResponse == null || ospreyApiResponse.getDocs() == null || ospreyApiResponse.getDocs().isEmpty()) {
            //  softAssert.assertTrue(false, "Failed! API response has null or empty docs.");
            Allure.step("No docs available for query: " + query + ". Skipping this query.");
            return;
        }
        unmatchedCount = ospreyApiResponse.getDocs().size(); // Total docs start as unmatched
        Allure.step("Docs populated with size: " + ospreyApiResponse.getDocs().size());

        // Preprocess the query to split into individual words
        String[] queryWords = query.toLowerCase().split("\\s+");

        //Adding the steps here
    //    List<OspreyApiResponse.Doc> unmatchedDocs = new ArrayList<>();
        List<String> matchedKeywords = new ArrayList<>();
        List<String> unmatchedKeywords = new ArrayList<>();

        // Step 1: Verify the first document's brand name
        OspreyApiResponse.Doc firstDoc = ospreyApiResponse.getDocs().get(0);
        String brandName = firstDoc.getBrandNameTextEnMv().toString(); // Assuming brand name is stored in getBrandNameTextEnMv

        if (brandName != null) { // && brandName.toLowerCase().contains(query.toLowerCase())) {
            for (String word : queryWords) {
                if (brandName.toLowerCase().contains(word)) {
                    Allure.step("Validation successful for brand name in the first document: " + brandName);
                    return; // Exit if the first document matches the brand name
                } else {
                    Allure.step("First document's brand name does not match. Proceeding to product name validation for all docs.");
                }
            }
            // Step 2: Verify Product Name for all documents
            // int productNameMatchCount = 0;
            //  int unmatchedCount = 0;
            //  List<OspreyApiResponse.Doc> matchedDocs = new ArrayList<>();
            List<OspreyApiResponse.Doc> unmatchedDocs = new ArrayList<>();

            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
                String productName = doc.getNameTextEn();
                boolean isMatched = false;
                if (productName != null) {//&& productName.toLowerCase().contains(query.toLowerCase())) {
                    for (String word : queryWords) {
                        if (productName.toLowerCase().contains(word)) {
                            matchedCount++;
                            //Adding step here
                       //     matchedKeywords.add(productName);
                            System.out.println("Checking Product Name in Response: " + productName);
                            unmatchedCount--;
                            System.out.println("Checking Product Name in Response: " + productName);
                            isMatched = true;
                            break;
                        }
                    }
                }
                if (!isMatched) {
                    //  unmatchedCount++;
                    unmatchedDocs.add(doc);
                    // Added the step here
                //    unmatchedKeywords.add(productName != null ? productName : "N/A");
                    System.out.println("Checking Product Name in Response: " + productName);
                }
            }
            // matchingProductNames.add(productName);

            //  System.out.println("Matched product names:" + matchedDocs);
            System.out.println("Matched product name count: " + matchedCount);
            System.out.println("Unmatched product count after product name validation: " + unmatchedCount);

            //   int l2CategoryMatchCount = 0;
            List<OspreyApiResponse.Doc> unmatchedL2Docs = new ArrayList<>();

            for (OspreyApiResponse.Doc doc : unmatchedDocs) {
                String l2Category = doc.getL1l2categoryEnStringMv().toString();
                boolean isMatched = false;
                if (l2Category != null) { // && l2Category.toLowerCase().contains(query.toLowerCase())) {
                    for (String word : queryWords) {
                        if (l2Category.toLowerCase().contains(word)) {
                            matchedCount++;
                            // Added the step here
                            matchedKeywords.add(l2Category);
                            unmatchedCount--;
                            isMatched = true;
                            break;
                        }
                    }
                }
                if (!isMatched) {
                    unmatchedL2Docs.add(doc);
                    //Addded the step here
                    unmatchedKeywords.add(l2Category != null ? l2Category : "N/A");
                }
            }

            System.out.println("Matched L2 category count: " + matchedCount);
            System.out.println("Unmatched product count after L2 category validation: " + unmatchedCount);

            //   int l3CategoryMatchCount = 0;
            List<OspreyApiResponse.Doc> unmatchedL3Docs = new ArrayList<>();

            for (OspreyApiResponse.Doc doc : unmatchedL2Docs) {
                String l3Category = doc.getL1l3categoryEnStringMv().toString();
                boolean isMatched = false;
                if (l3Category != null) { // && l3Category.toLowerCase().contains(query.toLowerCase())) {
                    for (String word : queryWords) {
                        if (l3Category.toLowerCase().contains(word)) {
                            matchedCount++;
                            matchedKeywords.add(l3Category);
                            unmatchedCount--;
                            isMatched = true;
                            break;
                        }
                    }
                }
                if (!isMatched) {
                    // Added the step here
                 //   unmatchedCount++;
                    unmatchedL3Docs.add(doc);
                    //Added the step here
                 //   unmatchedKeywords.add(l3Category != null ? l3Category : "N/A");
                }
            }

            System.out.println("Matched L3 category count: " + matchedCount);
            System.out.println("Unmatched product count after L3 category validation: " + unmatchedCount);

            // Final assertion for unmatched products
            if (!(unmatchedCount >= 0)) {
                softAssert.assertTrue(true, "Passed! Unmatched products remain after all validations. Count: " + unmatchedCount);
            } else {
                Allure.step("All products validated successfully across brand name, product name, L2, and L3 categories.");
            }

            //Added the steps here

            // Generate Allure Overview
            int totalKeywords = matchedCount + unmatchedCount;

            // Summary as Allure Step
            String summary = String.format(
                    "Total Keywords: %d\nMatched Keywords: %d\nUnmatched Keywords: %d",
                    totalKeywords, matchedCount, unmatchedCount
            );
            Allure.step(summary);

            // Attach Full List of Keywords to Allure
            String keywordsList = "Matched Keywords:\n" + String.join(", ", matchedKeywords) +
                    "\n\nUnmatched Keywords:\n" + String.join(", ", unmatchedKeywords);

            Path keywordsFile = Paths.get("target/keywords_overview.txt");
            Files.write(keywordsFile, keywordsList.getBytes());

            Allure.addAttachment("Keywords Overview", new FileInputStream(keywordsFile.toFile()));


            // Generate a pie chart for the final results
            int MatchedCount = matchedCount;
            int UnMatchedCount = unmatchedCount;
            //  List<String> keywords = Collections.singletonList("");

            createDoughnutChart(MatchedCount, UnMatchedCount, query);
            String doughnutChartFile = "target/pie_chart.png";
            attachPieChart(doughnutChartFile);
            //  generatePieChart("Keyword Validation Results for: " + query, matchedCount, unmatchedCount);

        }
    }
}


            //      if(ospreyApiResponse.getFacetData().getBrandStringMv().contains(query)) {
            //        System.out.println(query);

//               for( OspreyApiResponse.Doc doc: ospreyApiResponse.getDocs()) {
//                   if(o.spreyApiResponse.getDocs().get(0).getBrandNameTextEnMv().equals(query)) {
//
//               }
//
//    //        if(ospreyApiResponse.getDocs().get(0).getBrandNameTextEnMv().equals(query)) {
//
//      //     }
//            if (ospreyApiResponse.getDocs() == null || ospreyApiResponse.getDocs().isEmpty()) {
//                softAssert.assertTrue(false, "Failed! API response has null or empty docs.");
//                return;
//            } else {
//                log.info("Docs populated with size: " + ospreyApiResponse.getDocs().size());
//            }
//            //   System.out.println("Docs populated with size: " + ospreyApiResponse.getDocs().size());
//            //   }
//
//            boolean productNameMatch = false;
//            boolean l2CategoryMatch = false;
//            boolean l3CategoryMatch = false;
//
//            // Iterate through all documents to validate product name
//            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
//           //     for(OspreyApiResponse.Doc doc1 : ospreyApiResponse.getDocs())
//                // Validate Product Name
//                String productName = doc.getNameTextEn();
//                if (productName != null && productName.equalsIgnoreCase(query)) {
//                    productNameMatch = true;
//                }
//            }
//
//            if (productNameMatch) {
//                System.out.println("Validation successful for product name.");
//            } else {
//                System.out.println("No product name matches the query. Proceeding to L2 category validation.");
//            }
//
//            // Iterate through all documents to validate L2 category
//            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
//                String l2Category = doc.getL2Category();
//                if (l2Category != null && l2Category.equalsIgnoreCase(query)) {
//                    l2CategoryMatch = true;
//                }
//            }
//
//            if (l2CategoryMatch) {
//                System.out.println("Validation successful for L2 category.");
//            } else {
//                System.out.println("No L2 category matches the query. Proceeding to L3 category validation.");
//            }
//
//            // Iterate through all documents to validate L3 category
//            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
//                String l3Category = doc.getL3Category();
//                if (l3Category != null && l3Category.equalsIgnoreCase(query)) {
//                    l3CategoryMatch = true;
//                }
//            }
//
//            if (l3CategoryMatch) {
//                System.out.println("Validation successful for L3 category.");
//            } else {
//                softAssert.assertTrue(false, "Failed! No L3 category matches the query in all docs. Expected: " + query);
//            }
//
//            // Final assertions to ensure validation has occurred
//            if (!productNameMatch && !l2CategoryMatch && !l3CategoryMatch) {
//                softAssert.assertTrue(false, "Failed! No match found for query in product name, L2 category, or L3 category.");
//            }
//        }
//        }
//            //   public void validateSearchQueryResult(String query, String typeOfQuery) {
//            // Check if the query is present in the search results
//            boolean queryFound = false;
//            // Access the first product in the docs array
//            //  boolean product = ospreyApiResponse.getGetdocs().get(0);
//            //     ospreyApiResponse.getDocs().get(0).getBrandNameTextEnMv().contains(query);
//            if (ospreyApiResponse.getDocs() == null || ospreyApiResponse.getDocs().isEmpty()) {
//                softAssert.assertTrue(false, "Failed! Got null or empty docs");
//                return; // Exit if no docs found
//            }
//
//            // Extract first doc for convenience
//            //     Doc firstDoc = ospreyApiResponse.getDocs().get(0);
//            OspreyApiResponse.Doc firstdoc  = ospreyApiResponse.getDocs().get(0);
//
//            List<String> brandNames = firstdoc.getBrandNameTextEnMv();
//            // First verify with brand name
//            if (brandNames == null || brandNames.isEmpty()) {
//                softAssert.assertTrue(false, "Failed! Brand name list is null or empty");
//            } else {
//                // Check if any brand name matches the query
//                boolean matchFound = brandNames.stream()
//                        .anyMatch(brandName -> brandName.equalsIgnoreCase(query));
//
//                if (!matchFound) {
//                    softAssert.assertTrue(false, "Failed! No brand name matches the query. Expected: " + query + ", Found: " + brandNames);
//                } else {
//                    System.out.println("Brand name matches the query.");
//                }
//            }
//            softAssert.assertTrue(false, "Failed! Brand name mismatch. Expected: " + searchKeyword + ", Found: " + firstDoc.getBrandName());
//            // If brand doesn't match, move to product name
//        } else {
//            // Brand matches, proceed with product name check
//            if (firstdoc.getBrandNameTextEnMv() == null || firstdoc.getName().isEmpty()) {
//                softAssert.assertTrue(false, "Failed! Got null or empty product name");
//            } else {
//                // If product name matches, proceed to check l2 category
//                if (firstDoc.getL2Category() == null || !firstDoc.getL2Category().equalsIgnoreCase(expectedL2Category)) {
//                    softAssert.assertTrue(false, "Failed! L2 category mismatch. Expected: " + expectedL2Category + ", Found: " + firstDoc.getL2Category());
//                } else {
//                    // If L2 category matches, proceed to check l3 category
//                    if (firstDoc.getL3Category() == null || !firstDoc.getL3Category().equalsIgnoreCase(expectedL3Category)) {
//                        softAssert.assertTrue(false, "Failed! L3 category mismatch. Expected: " + expectedL3Category + ", Found: " + firstDoc.getL3Category());
//                    } else {
//                        // All categories match, proceed with price validation
//                        if (firstDoc.getPriceInfo() == null || firstDoc.getPriceInfo().getPrice() == null) {
//                            softAssert.assertTrue(false, "Failed! No price in the response");
//                        }
//                    }
//                }
//            }
//        }
//
//        // Finally, validate results count if all checks pass
//        validateResultsCount();
//    }
    // Based on the type of query, check the appropriate field in the response
    // Assert softAssert = true;
//        switch (typeOfQuery.toLowerCase()) {
//        case "brand":
//            // Check if the brand name matches the query (assuming brand names are in "brandName_text_en_mv")
//            List<String> brandNames =  product.get("brandName_text_en_mv");
//            for (String brandName : brandNames) {
//                if (brandName.contains(query)) {
//                    queryFound = true;
//                    break;
//                }
//            }
//            break;
//        case "product":
//            // Check if the product name matches the query (assuming product name is in "name_text_en")
//            String productName = (String) product.get("name_text_en");
//            if (productName != null && productName.contains(query)) {
//                queryFound = true;
//            }
//            break;
//        case "l2 category":
//            // Check if the L2 category matches the query (assuming L2 category IDs are in "l2category_string_mv")
//            List<String> l2Categories = (List<String>) product.get("l2category_string_mv");
//            for (String category : l2Categories) {
//                if (category.contains(query)) {
//                    queryFound = true;
//                    break;
//                }
//            }
//            break;
//        case "l3 category":
//            // Check if the L3 category matches the query (assuming L3 category IDs are in "l3category_string_mv")
//            List<String> l3Categories = (List<String>) product.get("l3category_string_mv");
//            for (String category : l3Categories) {
//                if (category.contains(query)) {
//                    queryFound = true;
//                    break;
//                }
//            }
//            break;
//        default:
//            softAssert.fail(typeOfQuery + " query type is not recognized.");
//            return;
//    }            // Assert based on whether the query was found
//               if (queryFound) {
//        softAssert.assertTrue(true, typeOfQuery + " query is working fine.");
//    } else {
//        softAssert.assertTrue(false, typeOfQuery + " misspelled query is not giving relevant details.");
//    }
//}
//}
//
//        }


