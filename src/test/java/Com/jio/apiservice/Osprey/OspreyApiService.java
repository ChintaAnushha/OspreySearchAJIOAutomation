package Com.jio.apiservice.Osprey;

import Com.jio.model.OspreySearch.OspreyApiResponse;
import Com.jio.validateResponse.OspreySearch.OspreyAPIFiltersResponseValidator;
import Com.jio.validateResponse.OspreySearch.OspreyAPIResponseValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Com.jio.base.ApiUrls;
import Com.jio.base.BaseScript;
import Com.jio.model.OspreySearch.OspreyApiRequest;
import Com.jio.model.survey.global.TestData;
import Com.jio.util.ExcelReader;
import Com.jio.util.ObjectMapperWrapper;
import Com.jio.util.MicrositeYamlPathConstants;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.eclipse.packager.io.FileSystemSpoolOutTarget;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Listeners({ AllureTestNg.class })
public class OspreyApiService extends BaseScript{
    private SoftAssert softAssert;

    ApiUrls apiUrls = new ApiUrls();
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    HttpResponse httpResponse = null;
    TestData testData = null;

    OspreyApiRequest ospreyAPIRequest = null;

    String ospreyApiRequestString = null;

   public OspreyApiResponse ospreyApiResponse = new OspreyApiResponse();
    OspreyAPIResponseValidator ospreyAPIResponseValidator = null;

    OspreyAPIFiltersResponseValidator ospreyAPIFiltersResponseValidator = null;


    @Step("Validate Osprey API with valid details")
    @Description("Validates the Osprey API response with valid input parameters and verifies all response fields")
    public void ospreyAPIWithValidDetails() {
        try {
            SoftAssert softAssert = new SoftAssert();
            this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.SEARCH_KEYWORDS_DATAKEY);
            ExcelReader excelReader = new ExcelReader();
        //    List<String> queries = excelReader.readExcel("./OspreySearchAJIO/testdata/keywords.xlsx");
            List<String> queries = excelReader.readExcel("./testdata/keywords.xlsx");
            for (String query : queries) {
                Map<String, String> testDataParams = testData.getOtherParams();
                ospreyAPIRequest = new OspreyApiRequest();
                ospreyAPIRequest.setQuery(query);
                ospreyAPIRequest.setStore(testDataParams.get("store"));
                String recordsPerPageString = testDataParams.get("records_per_page");

// Convert to integer and set it
            //    ospreyAPIRequest.setRecordsPerPage(Integer.parseInt(recordsPerPageString));
                ospreyAPIRequest.setRecordsperpage(Integer.parseInt(recordsPerPageString));

                ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
                System.out.println("Search request body: " + ospreyApiRequestString);
                //  createRequest(softAssert);
                executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse,testData);
                ospreyAPIResponseValidator.validateSearchqueryResults(query,softAssert,query);
                ospreyAPIResponseValidator.validateTop10Results(query);
      //   OspreyAPIResponseValidator = new CreateSurveyResponseValidator(createSurveyResponseWrapper,testData);
      //    createSurveyResponseValidator.validateCreatedSurveyResponse(softAssert);
                 softAssert.assertAll();
//                int MatchedCount = 0;
//                int UnMatchedCount = 0;
//                List<String> keywords = Collections.singletonList("");
//
//                createDoughnutChart(MatchedCount, UnMatchedCount, keywords);
//                String doughnutChartFile = "target/pie_chart.png";
//                attachPieChart(doughnutChartFile);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Execute Osprey API search with valid query")
    @Description("Validates the Osprey API search functionality with a valid search query and verifies the response")
    public void ospreyAPIWithValidQuery() {
        try {
            softAssert = new SoftAssert();

            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.SEARCH_KEYWORDS_VALID_DATAKEY);
                    });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));

                    });

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);

            //  createRequest(softAssert);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            // Validate response
            Allure.step("Validating response", () -> {
            ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
            ospreyAPIResponseValidator.validateBasicResponse();
            ospreyAPIResponseValidator.validateQueryResults();
            ospreyAPIResponseValidator.validateProductDetails();
        });

                softAssert.assertAll();
            } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Valid query test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Execute Osprey API search with valid query")
    @Description("Validates the Osprey API search functionality with a valid search query and verifies the response")
    public void ospreyAPIWithInValidQuery() {
        try {
            softAssert  = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_INVALID_DATAKEY);
                    });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                    });

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.attachment("Request Body", ospreyApiRequestString);

            //  createRequest(softAssert);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validateQueryResults();
                        ospreyAPIResponseValidator.validateProductDetails();
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Invalid query test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API behavior with empty search query")
    @Description("Validates that the API returns appropriate error message when search query is empty")
    public void ospreyAPIWithEmptyQuery() {
        try {
            softAssert = new SoftAssert();

            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_EMPTY_DATAKEY);
                    });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        String recordsPerPageString = testDataParams.get("records_per_page");
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(recordsPerPageString));  // Convert to integer and set it
                    });

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);

            //  createRequest(softAssert);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            // Validate response
            Allure.step("Validating response", () -> {
            ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
            //   ospreyAPIResponseValidator.validateBasicResponse();
            ospreyAPIResponseValidator.validateEmptySearchQueryError(ospreyApiResponse);
        });
            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Empty query test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API response with invalid store")
    @Description("This test validates the API behavior when an invalid store value 'rilfn' is provided. Expected: Error response with 422 status code and 'Invalid store' message.")
    public void ospreyAPIWithInvalidStore() {
        try {
            softAssert  = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_INVALIDSTORE_DATAKEY);
                    });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

        //    Allure.step("Setting request parameters", () -> {
            String query = testDataParams.get("query"); // Extracting Query
            String store = testDataParams.get("store"); // Extracting Store
            String pageNumber = testDataParams.get("pageNumber");
            ospreyAPIRequest.setQuery(testDataParams.get("query"));
            ospreyAPIRequest.setStore(testDataParams.get("store"));
            String recordsPerPageString = testDataParams.get("records_per_page");
            ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
            ospreyAPIRequest.setRecordsperpage(Integer.parseInt(recordsPerPageString));   // Convert to integer and set it

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.attachment("Request Body", ospreyApiRequestString);

            // Execute API Request
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            Allure.step("Validating response", () -> {
            ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);

            // Validate Invalid Store
                ospreyAPIResponseValidator.validateInvalidStoreResponse(query, store); //Pass query here
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Invalid store test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API response when store parameter is missing")
    @Description("This test validates the API behavior when the required store parameter is missing from the request body. Expected: Error response with 422 status code and field required message.")
    public void ospreyAPIWithNoStore() {
        try {
            softAssert  = new SoftAssert();
            Allure.step("Loading test data", () -> {
                this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_NOSTORE_DATAKEY);

            });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

         //   Allure.step("Setting request parameters", () -> {
                        String query = testDataParams.get("query"); // Extracting Query
                        String store = testDataParams.get("store"); // Extracting Store
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        String recordsPerPageString = testDataParams.get("records_per_page");
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(recordsPerPageString));   // Convert to integer and set it
              //      });

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.attachment("Request Body", ospreyApiRequestString);

            // Execute API Request
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);

                        // Validate Invalid Store
                        ospreyAPIResponseValidator.validateMissingFieldResponse(query, store); //Pass query and store here
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("No Store test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API product search with price asc filter")
    @Description("Test to verify product search with price sorting")
    public void productSearchWithPriceAscSorting() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_PRICEASC_DATAKEY);
                    });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setSortOrder(testDataParams.get("sort_order"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                    });

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.attachment("Request Body", ospreyApiRequestString);

            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validatePriceAscSortingResponse();
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Price Asc sorting test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API product search with price desc filter")
    @Description("Test to verify product search with price descending sorting")
    public void productSearchWithPriceDescSorting() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_PRICEDESC_DATAKEY);
                    });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setSortOrder(testDataParams.get("sort_order"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                    });

            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);

            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validatePriceDescSortingResponse();
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Price Desc sorting test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API search with disabled facets")
    @Description("This test verifies that the API response does not include facet data when disable_facets is set to true")
    public void ospreyAPIWithDisableFacets() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_DISABLEDFACET_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set basic request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));

                        // Set disable_facets to true
                        ospreyAPIRequest.setDisableFacets(true);
                    });
            // Convert request to string and execute
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);

            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
//            ospreyAPIResponseValidator.validateQueryResults();
//            ospreyAPIResponseValidator.validateProductDetails();
                        ospreyAPIResponseValidator.validateDisabledFacets();
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());

            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Disable facet true test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API search with enabled facets")
    @Description("This test verifies that the API response include facet data when disable_facets is set to false")
    public void ospreyAPIWithEnabledFacets() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_ENABLEDFACET_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));

                        // Set disable_facets to false
                        ospreyAPIRequest.setDisableFacets(false);
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
//            ospreyAPIResponseValidator.validateQueryResults();
//            ospreyAPIResponseValidator.validateProductDetails();
                        ospreyAPIResponseValidator.validateEnabledFacets();  // New validation method
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Enable facet false test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API search with disabled facet counts")
    @Description("This test verifies that the API response shows null values when enable_facet_counts is set to false")
    public void ospreyAPIWithDisabledFacetCounts() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_DISABLEDFACETCOUNT_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));

                        // Set enable_facet_counts to false
                        ospreyAPIRequest.setEnableFacetCounts(false);
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validateDisabledFacetValueCounts();  // New validation method
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Disable Facet count test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API search with Enabled facet counts")
    @Description("This test verifies that the API response shows count values when enable_facet_counts is set to true")
    public void ospreyAPIWithEnabledFacetCounts() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_ENABLEDFACETCOUNT_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));

                        // Set enable_facet_counts to false
                        ospreyAPIRequest.setEnableFacetCounts(true);
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);
            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validateEnabledFacetValuesCounts();  // New validation method
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Enable facets test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API search with specific attributes")
    @Description("This test verifies that the API response includes only specified attributes in docs")
    public void ospreyAPIWithAttributesRetrieval() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_SPECIFICATTRIBUTE_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set basic request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                    });
            // Set attributes to retrieve
            List<String> attributesToRetrieve = Arrays.asList("name_text_en");
            ospreyAPIRequest.setAttributesToRetrieve(attributesToRetrieve);

            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateAttributesRetrieval();
                    });

            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Specific Attributes test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API store redirect")
    @Description("This test verifies that the API redirects to correct store when no results found")
    public void ospreyAPIWithStoreRedirect() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_STOREREDIRECT_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                    });

            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateStoreRedirect();
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Redirect for store failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API missing field error")
    @Description("This test verifies the error response when required store field is missing")
    public void ospreyAPIExceptQueryNoOtherAttribute() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_EXCEPTQUERYNOOTHERATTRIBUTE_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            String query = testDataParams.get("query"); // Extracting Query
            String store = testDataParams.get("store"); // Extracting Store

            // Set only query parameter
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        //  ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                        //   ospreyAPIRequest.setStore(testDataParams.get("store"));
                        //   String recordsPerPageString = testDataParams.get("records_per_page");
                        //    ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        //    ospreyAPIRequest.setRecordsperpage(Integer.parseInt(recordsPerPageString));   // Convert to integer and set it

                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate error response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateMissingFieldResponse(query, store);
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Error message for Invalid Field : " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API pagination")
    @Description("This test verifies the API response for specific page number")
    public void ospreyAPIPagination() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_PAGINATION_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number")));
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            Allure.step("Executing API request", () -> {
                        System.out.println("Search request body: " + ospreyApiRequestString);
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validatePageNumberResponse();
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Pagination test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API invalid page number error")
    @Description("This test verifies the error response when page_number is not an integer")
    public void ospreyAPIInvalidPageNumber() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_INVALIDPAGINATION_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                        ospreyAPIRequest.setSortfield(testDataParams.get("sort_field"));
                        // Set page number as string without parsing
                        ospreyAPIRequest.setPageNumberValue(testDataParams.get("page_number"));
                    });
         //   ospreyAPIRequest.setPageNumber(Integer.parseInt(testDataParams.get("page_number"))); // Pass string value

            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        //  ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validateInvalidPageNumberError();
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Invalid page number test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API invalid records offset error")
    @Description("This test verifies the error response when records_offset is not an integer")
    public void ospreyAPIInvalidRecordsOffset() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_INVALIDRECORDOFFSET_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsOffset(testDataParams.get("records_offset"));
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);
            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        //   ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validateInvalidRecordsOffsetError();
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Invalid records offset test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API records offset")
    @Description("This test verifies the API response with records offset")
    public void ospreyAPIRecordsOffset() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
                        this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_RECORDSOFFSET_DATAKEY);
                    });
            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsOffset(testDataParams.get("records_offset"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);

            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });
            // Validate response
            Allure.step("Validating response", () -> {
                        ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
                        ospreyAPIResponseValidator.validateBasicResponse();
                        ospreyAPIResponseValidator.validateRecordsOffset();
                    });
            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Records offset test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Step("Verify Osprey API records per page")
    @Description("This test verifies the API response respects records_per_page parameter")
    public void ospreyAPIRecordsPerPage() {
        try {
            softAssert = new SoftAssert();
            Allure.step("Loading test data", () -> {
            this.testData = getYMLData(MicrositeYamlPathConstants.SEARCH_KEYWORDS_FILEPATH, MicrositeYamlPathConstants.PRODUCT_SEARCH_WITH_RECORDSPERPAGE_DATAKEY);
            });

            Map<String, String> testDataParams = testData.getOtherParams();
            ospreyAPIRequest = new OspreyApiRequest();

            // Set request parameters
            Allure.step("Setting request parameters", () -> {
                        ospreyAPIRequest.setQuery(testDataParams.get("query"));
                        ospreyAPIRequest.setStore(testDataParams.get("store"));
                        ospreyAPIRequest.setRecordsperpage(Integer.parseInt(testDataParams.get("records_per_page")));
                    });
            // Execute request
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.attachment("Request Body", ospreyApiRequestString);

            System.out.println("Search request body: " + ospreyApiRequestString);

            Allure.step("Executing API request", () -> {
                        executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                    });

            Allure.step("Validating response", () -> {
            // Validate response
            ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse, testData);
            ospreyAPIResponseValidator.validateBasicResponse();
            ospreyAPIResponseValidator.validateRecordsPerPage();
            });

            Allure.attachment("Response Body", ospreyApiResponse.toString());
            softAssert.assertAll();
        } catch (Exception ex) {
            Allure.step("Test failed: " + ex.getMessage(), Status.FAILED);
            Assert.fail("Records per page test failed: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public void createRequest(SoftAssert softAssert) {
        Map<String, String> testDataParams = testData.getOtherParams();
        ospreyAPIRequest =new OspreyApiRequest();
        ospreyAPIRequest.setQuery(testDataParams.get("query"));
        ospreyAPIRequest.setStore(testDataParams.get("store"));

        try {
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            Allure.step("Add OspreySearch Request Body: " + ospreyApiRequestString);
        } catch (JsonProcessingException e) {
            softAssert.assertTrue(false, "Failed! - Error while parsing request object. Please refer Stack trace: " + e.getMessage());
            Allure.step(e.getMessage());
        }
    }

    public void executeRequestAndGetResponse(SoftAssert softAssert,String request) {
        String response = null;
//        queryParam = new HashMap<String, String>();
        httpResponse=apiUrls.postOspreyAPI(testData,ospreyApiRequestString);
        softAssert.assertEquals(httpResponse.getStatusLine().getStatusCode(), Integer.parseInt(testData.getOtherParams().get("expectedStatusCode")), "FAILED! API did not respond with status 200");
        try {

            response = EntityUtils.toString(httpResponse.getEntity());
            Allure.step("API Response::::: " + response);
            if (httpResponse.getStatusLine().getStatusCode() == Integer.parseInt(testData.getOtherParams().get("expectedStatusCode"))) {
                Allure.step("OspreySearch ResponseCODE:::::  " + httpResponse.getStatusLine().getStatusCode());
                ospreyApiResponse = objectMapper.readValue(response,
                        OspreyApiResponse.class);
            } else {
                Allure.step("Http Response is not OK: " + httpResponse.getStatusLine().getStatusCode());
                softAssert.assertTrue(false, "Failed! UnExpected error code:::::" + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (final IOException e) {
            softAssert.assertTrue(false, "Failed! - Error while processing OspreySearch response. Please refer Stack trace: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



