package Com.jio.apiservice.Osprey;

import Com.jio.model.OspreySearch.OspreyApiResponse;
import Com.jio.validateResponse.OspreySearch.OspreyAPIResponseValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Com.jio.base.ApiUrls;
import Com.jio.base.BaseScript;
import Com.jio.model.OspreySearch.OspreyApiRequest;
import Com.jio.model.survey.global.TestData;
import Com.jio.util.ExcelReader;
import Com.jio.util.MicrositeYamlPathConstants;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OspreyApiService extends BaseScript{

    ApiUrls apiUrls = new ApiUrls();
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    HttpResponse httpResponse = null;
    TestData testData = null;

    OspreyApiRequest ospreyAPIRequest = null;

    String ospreyApiRequestString = null;

    OspreyAPIResponseValidator ospreyAPIResponseValidator = null;

    OspreyApiResponse ospreyApiResponse = new OspreyApiResponse();
  //  OspreyAPIResponseValidator ospreyAPIResponseValidator  = null;
//    GetFaqResponse getFaqResponse = new GetFaqResponse();
//    GetFaqResponseValidator getFaqResponseValidator = null;

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

                ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
                System.out.println("Add search request body: " + ospreyApiRequestString);
                //  createRequest(softAssert);
                executeRequestAndGetResponse(softAssert, ospreyApiRequestString);
                ospreyAPIResponseValidator = new OspreyAPIResponseValidator(ospreyApiResponse,testData);
           //     ospreyAPIResponseValidator.validateSearchqueryResults(query,softAssert);
                ospreyAPIResponseValidator.validateTop10Results(query);
      //   OspreyAPIResponseValidator = new CreateSurveyResponseValidator(createSurveyResponseWrapper,testData);
      //    createSurveyResponseValidator.validateCreatedSurveyResponse(softAssert);
                 softAssert.assertAll();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createRequest(SoftAssert softAssert) {
        Map<String, String> testDataParams = testData.getOtherParams();
        ospreyAPIRequest =new OspreyApiRequest();
        ospreyAPIRequest.setQuery(testDataParams.get("query"));
        ospreyAPIRequest.setStore(testDataParams.get("store"));

        try {
            ospreyApiRequestString = objectMapper.writeValueAsString(ospreyAPIRequest);
            System.out.println("Add OspreySearch Request Body: " + ospreyApiRequestString);
        } catch (JsonProcessingException e) {
            softAssert.assertTrue(false, "Failed! - Error while parsing request object. Please refer Stack trace: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void executeRequestAndGetResponse(SoftAssert softAssert,String request) {
        String response = null;
//        queryParam = new HashMap<String, String>();
        httpResponse=apiUrls.postOspreyAPI(testData,ospreyApiRequestString);
        softAssert.assertEquals(httpResponse.getStatusLine().getStatusCode(), Integer.parseInt(testData.getOtherParams().get("expectedStatusCode")), "FAILED! API did not respond with status 200");
        try {

            response = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("API Response::::: " + response);
            if (httpResponse.getStatusLine().getStatusCode() == Integer.parseInt(testData.getOtherParams().get("expectedStatusCode"))) {
                System.out.println("OspreySearch ResponseCODE:::::  " + httpResponse.getStatusLine().getStatusCode());

            } else {
                System.out.println("Http Response is not OK: " + httpResponse.getStatusLine().getStatusCode());
                softAssert.assertTrue(false, "Failed! UnExpected error code:::::" + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (final IOException e) {
            softAssert.assertTrue(false, "Failed! - Error while processing OspreySearch response. Please refer Stack trace: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



//
// https://microsite-sit.ril.com/couturesearch/v1/products/search  IS THIS THE CORRECT URL?? No
//http://osprey-sit.services.ajio.com/couturesearch/v1/products/search