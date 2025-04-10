package Com.jio.test.OspreySearch.productSearch;

import Com.jio.apiservice.Osprey.apiService.productSearch.Suggestions.service.SuggestionsService;
import Com.jio.base.BaseScript;
import org.testng.annotations.Test;

public class SuggestionsTest extends BaseScript {

    SuggestionsService suggestionsService = new SuggestionsService();

    @Test(priority = 0)
    public void suggestionsWithValidDetails(){
        suggestionsService.executeSuggestionsAPIOk();
    }

    @Test(priority = 1)
    public void suggestionsWithoutUserId(){
        suggestionsService.executeSuggestionsAPIWithoutUserId();
    }

    @Test(priority = 2)
    public void suggestionsWithInvalidUserId(){
        suggestionsService.executeSuggestionsAPIWithInvalidUserId();
    }

    @Test(priority = 3)
    public void suggestionsWithoutPartnerId(){
        suggestionsService.executeSuggestionsAPIWithoutPartnerId();
    }

    @Test(priority = 4)
    public void suggestionsWithInvalidPartnerId(){
        suggestionsService.executeSuggestionsAPIWithInvalidPartnerId();
    }

    @Test(priority = 5)
    public void suggestionsWithInvalidQuery(){
        suggestionsService.executeSuggestionsAPIWithInvalidQuery();
    }

    @Test(priority = 6)
    public void suggestionsWithoutQuery(){
        suggestionsService.executeSuggestionsAPIWithoutQuery();
    }
}
