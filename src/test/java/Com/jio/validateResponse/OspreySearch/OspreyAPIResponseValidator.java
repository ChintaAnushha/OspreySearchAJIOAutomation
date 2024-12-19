package Com.jio.validateResponse.OspreySearch;

import Com.jio.base.BaseScript;
import Com.jio.model.OspreySearch.OspreyApiResponse;
import Com.jio.model.survey.global.TestData;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
public class OspreyAPIResponseValidator extends BaseScript{
    public OspreyApiResponse ospreyApiResponse;

    TestData  testData = null;
        Map<String,String> tesDataParams = null;
       // OspreyApiResponse  ospreyApiResponse = null;

        public  OspreyAPIResponseValidator(OspreyApiResponse ospreyApiResponse, TestData data) {
            this.ospreyApiResponse = ospreyApiResponse;
            this.testData = data;
            this.tesDataParams = data.getOtherParams();
        }

    public void validateTop10Results(String query) {
        String title = null;
        for (int i = 0; i < 10; i++) {
            title = ospreyApiResponse.docs.get(0).getNameTextEn();//getdocs().get(0).getHits().get(i).getTitle();
            if (title.contains(query) == true) {
              //  logger.info("Result " + i + " is::::" + ospreyApiResponse.getDocs().get(0).getNameTextEn());//getHits().get(i).getTitle());
                System.out.println("Result " + i + " is::::" + ospreyApiResponse.getDocs().get(0).getNameTextEn());//getHits().get(i).getTitle());
            } else {
                System.out.println("Result " + i + " " + title + " is mismatch");
            }
        }
    }


        public  void validateSearchqueryResults(String query, SoftAssert softAssert)
        {
//               for( OspreyApiResponse.Doc doc: ospreyApiResponse.getDocs()) {
//                   if(ospreyApiResponse.getDocs().get(0).getBrandNameTextEnMv().equals(query)) {
//
//               }
//
//    //        if(ospreyApiResponse.getDocs().get(0).getBrandNameTextEnMv().equals(query)) {
//
//      //      }
            if (ospreyApiResponse.getDocs() == null || ospreyApiResponse.getDocs().isEmpty()) {
                softAssert.assertTrue(false,"Failed! API response has null or empty docs.");
                return;
            }else {
        System.out.println("Docs populated with size: " + ospreyApiResponse.getDocs().size());
    }

            boolean productNameMatch = false;
            boolean l2CategoryMatch = false;
            boolean l3CategoryMatch = false;

            // Iterate through all documents to validate product name
            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
           //     for(OspreyApiResponse.Doc doc1 : ospreyApiResponse.getDocs())
                // Validate Product Name
                String productName = doc.getNameTextEn();
                if (productName != null && productName.equalsIgnoreCase(query)) {
                    productNameMatch = true;
                }
            }

            if (productNameMatch) {
                System.out.println("Validation successful for product name.");
            } else {
                System.out.println("No product name matches the query. Proceeding to L2 category validation.");
            }

            // Iterate through all documents to validate L2 category
            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
                String l2Category = doc.getL2Category();
                if (l2Category != null && l2Category.equalsIgnoreCase(query)) {
                    l2CategoryMatch = true;
                }
            }

            if (l2CategoryMatch) {
                System.out.println("Validation successful for L2 category.");
            } else {
                System.out.println("No L2 category matches the query. Proceeding to L3 category validation.");
            }

            // Iterate through all documents to validate L3 category
            for (OspreyApiResponse.Doc doc : ospreyApiResponse.getDocs()) {
                String l3Category = doc.getL3Category();
                if (l3Category != null && l3Category.equalsIgnoreCase(query)) {
                    l3CategoryMatch = true;
                }
            }

            if (l3CategoryMatch) {
                System.out.println("Validation successful for L3 category.");
            } else {
                softAssert.assertTrue(false, "Failed! No L3 category matches the query in all docs. Expected: " + query);
            }

            // Final assertions to ensure validation has occurred
            if (!productNameMatch && !l2CategoryMatch && !l3CategoryMatch) {
                softAssert.assertTrue(false, "Failed! No match found for query in product name, L2 category, or L3 category.");
            }
        }
        }
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
