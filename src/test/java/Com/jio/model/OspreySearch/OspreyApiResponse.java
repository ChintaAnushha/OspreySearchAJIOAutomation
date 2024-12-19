package Com.jio.model.OspreySearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class  OspreyApiResponse{

    @JsonProperty("numFound")
    public int numFound;

    @JsonProperty("storeRedirect")
    public Object storeRedirect; // Can be null, so Object is used.

    @JsonProperty("docs")
    public List<Doc> docs = null;


    @JsonProperty("facetData")
    public FacetData facetData;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Doc {

        @JsonProperty("ageing_double")
        public double ageingDouble;

        @JsonProperty("averageRating_double")
        public double averageRatingDouble;

        @JsonProperty("brandName_text_en_mv")
        public List<String> brandNameTextEnMv;

        @JsonProperty("catalogId")
        public String catalogId;

        @JsonProperty("code_string")
        public String codeString;

        @JsonProperty("name_text_en")
        public String nameTextEn;

        @JsonProperty("price_inr_double")
        public double priceInrDouble;

        @JsonProperty("discount_double")
        public double discountDouble;

        @JsonProperty("size_string_mv")
        public List<String> sizeStringMv;

        @JsonProperty("color_string_mv")
        public List<String> colorStringMv;

        @JsonProperty("url")
        public String url;

        @JsonProperty("image")
        public String image;

        @JsonProperty("gender")
        public String gender;

        @JsonProperty("rating")
        public double rating;

        @JsonProperty("l1 category")
        public String l1Category;

        @JsonProperty("l2 category")
        public String l2Category;

        @JsonProperty("l3 category")
        public String l3Category;

        @JsonProperty("availability")
        public boolean availability;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FacetData {

        @JsonProperty("pricerange_inr_string")
        public List<Facet> priceRangeInrString;

        @JsonProperty("l1l3nestedcategory_en_string_mv")
        public List<Facet> l1l3NestedCategoryEnStringMv;

        @JsonProperty("brand_string_mv")
        public List<Facet> brandStringMv;

        @JsonProperty("discount_string")
        public List<Facet> discountString;

        @JsonProperty("size_string_mv")
        public List<Facet> sizeStringMv;

        @JsonProperty("color_string_mv")
        public List<Facet> colorStringMv;

        @JsonProperty("rating_double")
        public List<Facet> ratingDouble;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Facet {

        @JsonProperty("name")
        public String name;

        @JsonProperty("value")
        public int value;
    }
}
//@Getter
//@Setter
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class OspreyApiResponse {
//
//    @JsonProperty("getdocs")
//    public List<Object> getdocs = null;
//
//   // public List<Object> getdocs() {
//
//
////    @JsonProperty("results")
////    public List<Results> results = null;
//
//    public static class getDocs {
//
//        @JsonProperty("Docs")
//        public List<Doc> docs;
//
//    }
//    // public List<Object> getDocs() {
////   public List<getDocs> results = null;
//    //   }
//
//    public class BrandStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrandStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrickcollarEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrickcollarEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BricknecklineEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BricknecklineEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrickpatternEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrickpatternEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BricksleeveEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BricksleeveEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BricksportEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BricksportEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrickstyletypeEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class BrickstyletypeEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class DiscountString {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class DiscountString__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class Doc {
//        @JsonProperty("ageing_double")
//        public Float ageingDouble;
//
//        @JsonProperty("averageRating_double")
//        public Float averageRatingDouble;
//
//        @JsonProperty("brandName_text_en_mv")
//        public List<String> brandNameTextEnMv;
//
//        @JsonProperty("brandtype_en_string_mv")
//        public Object brandtypeEnStringMv;
//
//        @JsonProperty("brandtype_string_mv")
//        public List<String> brandtypeStringMv;
//
//        @JsonProperty("brickprimarycolor_en_string_mv")
//        public List<String> brickprimarycolorEnStringMv;
//
//        @JsonProperty("brickstyletype_en_string_mv")
//        public List<String> brickstyletypeEnStringMv;
//
//        @JsonProperty("catalogCommercialType_string")
//        public String catalogCommercialTypeString;
//
//        @JsonProperty("catalogId")
//        public String catalogId;
//
//        @JsonProperty("category_string_mv")
//        public List<String> categoryStringMv;
//
//        @JsonProperty("code_string")
//        public String codeString;
//
//        @JsonProperty("colorGroup_string")
//        public String colorGroupString;
//
//        @JsonProperty("commercialType_string")
//        public String commercialTypeString;
//
//        @JsonProperty("creationtime_date")
//        public String creationtimeDate;
//
//        @JsonProperty("discountType_string")
//        public String discountTypeString;
//
//        @JsonProperty("dod_enabled_int")
//        public Integer dodEnabledInt;
//
//        @JsonProperty("dod_price_inr_double")
//        public Float dodPriceInrDouble;
//
//        @JsonProperty("earlyAccessPrice_float")
//        public Object earlyAccessPriceFloat;
//
//        @JsonProperty("exactdiscount_int")
//        public Integer exactdiscountInt;
//
//        @JsonProperty("exclusiveTill_date")
//        public Object exclusiveTillDate;
//
//        @JsonProperty("extraImages_string_mv")
//        public List<String> extraImagesStringMv;
//
//        @JsonProperty("futureListPrice_inr_double")
//        public Object futureListPriceInrDouble;
//
//        @JsonProperty("golivedays_int")
//        public Integer golivedaysInt;
//
//        @JsonProperty("id")
//        public String id;
//
//        @JsonProperty("img-174Wx218H_string")
//        public String img174Wx218HString;
//
//        @JsonProperty("img-286Wx359H_string")
//        public String img286Wx359HString;
//
//        @JsonProperty("img-288Wx360H_string")
//        public String img288Wx360HString;
//
//        @JsonProperty("img-473Wx593H_string")
//        public String img473Wx593HString;
//
//        @JsonProperty("img-thumbNail_string")
//        public String imgThumbNailString;
//
//        @JsonProperty("inStockCount_double")
//        public Float inStockCountDouble;
//
//        @JsonProperty("inStockFlag_boolean")
//        public Boolean inStockFlagBoolean;
//
//        @JsonProperty("inTakeMarginPerc_double")
//        public Float inTakeMarginPercDouble;
//
//        @JsonProperty("isCodDisabled_boolean")
//        public Boolean isCodDisabledBoolean;
//
//        @JsonProperty("l1category_string_mv")
//        public List<String> l1categoryStringMv;
//
//        @JsonProperty("l1l2category_en_string_mv")
//        public List<String> l1l2categoryEnStringMv;
//
//        @JsonProperty("l1l3category_en_string_mv")
//        public List<String> l1l3categoryEnStringMv;
//
//        @JsonProperty("l2category_string_mv")
//        public List<String> l2categoryStringMv;
//
//        @JsonProperty("l3category_string_mv")
//        public List<String> l3categoryStringMv;
//
//        @JsonProperty("mrp_double")
//        public Float mrpDouble;
//
//        @JsonProperty("name_text_en")
//        public String nameTextEn;
//
//        @JsonProperty("numUserRatings_int")
//        public Integer numUserRatingsInt;
//
//        @JsonProperty("outfitPicture_string")
//        public Object outfitPictureString;
//
//        @JsonProperty("planningCategory_text_en")
//        public String planningCategoryTextEn;
//
//        @JsonProperty("priceValue_inr_double")
//        public Float priceValueInrDouble;
//        @JsonProperty("price_inr_double")
//        public Float priceInrDouble;
//        @JsonProperty("productToggleOn_string")
//        public Object productToggleOnString;
//        @JsonProperty("seasonCodeYear_string")
//        public Object seasonCodeYearString;
//        @JsonProperty("segmentProductTagValidity_date")
//        public Object segmentProductTagValidityDate;
//        @JsonProperty("segmentProductTag_string")
//        public Object segmentProductTagString;
//        @JsonProperty("size_en_string")
//        public String sizeEnString;
//        @JsonProperty("tradeDiscountedValue_double")
//        public Float tradeDiscountedValueDouble;
//        @JsonProperty("url_en_string")
//        public String urlEnString;
//        @JsonProperty("wasPrice_double")
//        public Float wasPriceDouble;
//        @JsonProperty("webcategory_string_mv")
//        public List<String> webcategoryStringMv;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class Example {
//        @JsonProperty("numFound")
//        public Integer numFound;
//        @JsonProperty("storeRedirect")
//        public Object storeRedirect;
//        @JsonProperty("docs")
//        public List<Doc> docs;
//        @JsonProperty("facetData")
//        public FacetData facetData;
//        @JsonProperty("brickcollar_en_string_mv")
//        public List<BrickcollarEnStringMv__1> brickcollarEnStringMv;
//        @JsonProperty("pricerange_inr_string")
//        public List<PricerangeInrString__1> pricerangeInrString;
//        @JsonProperty("l1l3nestedcategory_en_string_mv")
//        public List<L1l3nestedcategoryEnStringMv__1> l1l3nestedcategoryEnStringMv;
//        @JsonProperty("bricksport_en_string_mv")
//        public List<BricksportEnStringMv__1> bricksportEnStringMv;
//        @JsonProperty("brand_string_mv")
//        public List<BrandStringMv__1> brandStringMv;
//        @JsonProperty("occasion_en_string_mv")
//        public List<OccasionEnStringMv__1> occasionEnStringMv;
//        @JsonProperty("segmentcharacter_en_string_mv")
//        public List<SegmentcharacterEnStringMv__1> segmentcharacterEnStringMv;
//        @JsonProperty("brickpattern_en_string_mv")
//        public List<BrickpatternEnStringMv__1> brickpatternEnStringMv;
//        @JsonProperty("verticalfabrictype_en_string_mv")
//        public List<VerticalfabrictypeEnStringMv__1> verticalfabrictypeEnStringMv;
//        @JsonProperty("brickneckline_en_string_mv")
//        public List<BricknecklineEnStringMv__1> bricknecklineEnStringMv;
//        @JsonProperty("rating_string_mv")
//        public List<RatingStringMv__1> ratingStringMv;
//        @JsonProperty("golivedays_int")
//        public List<GolivedaysInt__1> golivedaysInt;
//        @JsonProperty("genderfilter_en_string_mv")
//        public List<GenderfilterEnStringMv__1> genderfilterEnStringMv;
//        @JsonProperty("verticalsizegroupformat_en_string_mv")
//        public List<VerticalsizegroupformatEnStringMv__1> verticalsizegroupformatEnStringMv;
//        @JsonProperty("bricksleeve_en_string_mv")
//        public List<BricksleeveEnStringMv__1> bricksleeveEnStringMv;
//        @JsonProperty("verticalsizegroup_en_string_mv")
//        public List<VerticalsizegroupEnStringMv__1> verticalsizegroupEnStringMv;
//        @JsonProperty("brickstyletype_en_string_mv")
//        public List<BrickstyletypeEnStringMv__1> brickstyletypeEnStringMv;
//        @JsonProperty("discount_string")
//        public List<DiscountString__1> discountString;
//        @JsonProperty("verticalcolorfamily_en_string_mv")
//        public List<VerticalcolorfamilyEnStringMv__1> verticalcolorfamilyEnStringMv;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class FacetData {
//        @JsonProperty("brickcollar_en_string_mv")
//        public List<BrickcollarEnStringMv> brickcollarEnStringMv;
//        @JsonProperty("pricerange_inr_string")
//        public List<PricerangeInrString> pricerangeInrString;
//        @JsonProperty("l1l3nestedcategory_en_string_mv")
//        public List<L1l3nestedcategoryEnStringMv> l1l3nestedcategoryEnStringMv;
//        @JsonProperty("bricksport_en_string_mv")
//        public List<BricksportEnStringMv> bricksportEnStringMv;
//        @JsonProperty("brand_string_mv")
//        public List<BrandStringMv> brandStringMv;
//        @JsonProperty("occasion_en_string_mv")
//        public List<OccasionEnStringMv> occasionEnStringMv;
//        @JsonProperty("segmentcharacter_en_string_mv")
//        public List<SegmentcharacterEnStringMv> segmentcharacterEnStringMv;
//        @JsonProperty("brickpattern_en_string_mv")
//        public List<BrickpatternEnStringMv> brickpatternEnStringMv;
//        @JsonProperty("verticalfabrictype_en_string_mv")
//        public List<VerticalfabrictypeEnStringMv> verticalfabrictypeEnStringMv;
//        @JsonProperty("brickneckline_en_string_mv")
//        public List<BricknecklineEnStringMv> bricknecklineEnStringMv;
//        @JsonProperty("rating_string_mv")
//        public List<RatingStringMv> ratingStringMv;
//        @JsonProperty("golivedays_int")
//        public List<GolivedaysInt> golivedaysInt;
//        @JsonProperty("genderfilter_en_string_mv")
//        public List<GenderfilterEnStringMv> genderfilterEnStringMv;
//        @JsonProperty("verticalsizegroupformat_en_string_mv")
//        public List<VerticalsizegroupformatEnStringMv> verticalsizegroupformatEnStringMv;
//        @JsonProperty("bricksleeve_en_string_mv")
//        public List<BricksleeveEnStringMv> bricksleeveEnStringMv;
//        @JsonProperty("verticalsizegroup_en_string_mv")
//        public List<VerticalsizegroupEnStringMv> verticalsizegroupEnStringMv;
//        @JsonProperty("brickstyletype_en_string_mv")
//        public List<BrickstyletypeEnStringMv> brickstyletypeEnStringMv;
//        @JsonProperty("discount_string")
//        public List<DiscountString> discountString;
//        @JsonProperty("verticalcolorfamily_en_string_mv")
//        public List<VerticalcolorfamilyEnStringMv> verticalcolorfamilyEnStringMv;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class GenderfilterEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class GenderfilterEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class GolivedaysInt {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class GolivedaysInt__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class L1l3nestedcategoryEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class L1l3nestedcategoryEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class OccasionEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class OccasionEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class PricerangeInrString {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class PricerangeInrString__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class RatingStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class RatingStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class SegmentcharacterEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class SegmentcharacterEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalcolorfamilyEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalcolorfamilyEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalfabrictypeEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalfabrictypeEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalsizegroupEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalsizegroupEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalsizegroupformatEnStringMv {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//
//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class VerticalsizegroupformatEnStringMv__1 {
//        @JsonProperty("name")
//        public String name;
//        @JsonProperty("value")
//        public Integer value;
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
