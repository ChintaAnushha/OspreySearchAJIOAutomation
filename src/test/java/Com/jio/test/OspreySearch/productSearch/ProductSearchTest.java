package Com.jio.test.OspreySearch.productSearch;

import Com.jio.apiservice.Osprey.apiService.productSearch.ProductSearch.service.ProductSearchService;
import Com.jio.base.BaseScript;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;

public class ProductSearchTest extends BaseScript {

    ProductSearchService productSearchService = new ProductSearchService();

    @Test(priority = 0)
    public void productSearchWithValidDetails() throws JsonProcessingException {
     //   productSearchService.executeProductSearchAPIOk();
    }

//    @Test(priority = 1)
//    public void productSearchWithInValidDetails() throws JsonProcessingException {
//        productSearchService.executeProductSearchAPIWithInvalidQuery();
//    }
//    @Test(priority = 2)
//    public void productSearchWithoutQuery() {
//        productSearchService.executeProductSearchAPIWithoutQuery();
//    }
//
//    @Test(priority = 3)
//    public void productSearchwithInvalidStore() {
//        productSearchService.executeProductSearchAPIWithInvalidStore();
//    }

//    @Test(priority = 4)
//    public void productSearchwithNoStore() {
//        productSearchService.executeProductSearchAPIWithNoStore();
//    }
//    @Test(priority = 2)
//    public void executeProductSearchWithoutVisitorId() {
//        productSearchService.executeProductSearchWithoutVisitorId();
//    }
//
//    @Test(priority = 5)
//    public void executeProductSearchAPIWithMisspelledQuery() {
//        productSearchService.executeProductSearchAPIWithMisspelledQuery();
//    }
//
//    @Test(priority = 6)
//    public void executeProductSearchAPIWithSynonymsQuery() {
//        productSearchService.executeProductSearchAPIWithSynonymsQuery();
//    }
//
//    @Test(priority = 5)
//    public void executeProductSearchAPIWithContextualQuery() {
//        productSearchService.executeProductSearchAPIWithContextualQuery();
//    }
//
//    @Test(priority = 6)
//    public void executeProductSearchAPIWithVernacularQuery() {
//        productSearchService.executeProductSearchAPIWithVernacularQuery();
//    }
//
//    @Test(priority = 7)
//    public void executeProductSearchAPIBoostQuery() {
//        productSearchService.executeProductSearchAPIBoostQuery();
//    }
//
//    @Test(priority = 8)
//    public void executeProductSearchAPIBuryQuery() {
//        productSearchService.executeProductSearchAPIBuryQuery();
//    }



}
