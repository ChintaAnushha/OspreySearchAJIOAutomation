package Com.jio.test.OspreySearch;

import Com.jio.apiservice.Osprey.OspreyApiService;
import Com.jio.base.BaseScript;
import org.testng.annotations.Test;


    public class GetKeywordTest extends BaseScript {
        OspreyApiService ospreyApiService = new OspreyApiService();

        @Test(priority =0)
        public void OspreyApiServiceWithValidDetails() {
            ospreyApiService.ospreyAPIWithValidDetails();
        }
}
//
//    @Test(priority =1)
//    public void addFaqWithInvalidCategory() {
//        addFaqService.addFaqWithInvalidCategory();
//    }
//
//    @Test(priority =2)
//    public void addFaqWithoutQuestion() {
//        addFaqService.addFaqWithoutQuestion();
//    }
//
//    @Test(priority =3)
//    public void addFaqWithoutAnswer() {
//        addFaqService.addFaqWithoutAnswer();
//    }