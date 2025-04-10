package Com.jio.test.OspreySearch.productSearch;

import Com.jio.apiservice.Osprey.apiService.productSearch.ProductSearchWithFilter.service.ProductSearchWithFiltersService;
import Com.jio.base.BaseScript;
import org.testng.annotations.Test;

public class ProductSearchWithFiltersTest extends BaseScript {

    ProductSearchWithFiltersService productSearchWithFiltersService = new ProductSearchWithFiltersService();

    @Test(priority = 0)
    public void productSearchWithFiltersApiOk() {
        productSearchWithFiltersService.executeProductSearchWithFiltersAPIOk();
    }

//    @Test(priority = 1)
//    public void executeProductSearchWithCategoryL4Filter() {
//        productSearchWithFiltersService.executeProductSearchWithCategoryL4Filter();
//    }
//
//    @Test(priority = 2)
//    public void executeProductSearchWithPriceFilter() {
//        productSearchWithFiltersService.executeProductSearchWithPriceFilter();
//    }
//
//    @Test(priority = 3)
//    public void executeProductSearchWithDiscountFilter() {
//        productSearchWithFiltersService.executeProductSearchWithDiscountFilter();
//    }
//
//    @Test(priority = 4)
//    public void executeProductSearchWithOnlyPriceFilter() {
//        productSearchWithFiltersService.executeProductSearchWithOnlyPriceFilter();
//    }
//
//    @Test(priority = 5)
//    public void executeProductSearchWithOnlyDiscountFilter() {
//        productSearchWithFiltersService.executeProductSearchWithOnlyDiscountFilter();
//    }
}
