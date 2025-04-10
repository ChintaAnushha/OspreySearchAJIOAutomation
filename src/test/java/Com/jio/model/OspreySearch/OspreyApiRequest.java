package Com.jio.model.OspreySearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OspreyApiRequest {

    @JsonProperty("query")
    public String query;

    @JsonProperty("store")
    public String store;

    @JsonProperty("records_per_page")
    public int recordsperpage;

    @JsonProperty("sort_field")
    public String sortfield;

//    @JsonProperty("filters")
//    public List<Filter> filters;

    @JsonProperty("filters")
    public List<Filter> filters;

    @JsonProperty("sort_order")
    public String sortOrder;

    @JsonProperty("page_number")
    public Object pageNumber;

    @JsonProperty("disable_facets")
    public boolean disableFacets;

    @JsonProperty("enable_facet_values_count")
    public boolean enableFacetValuesCount;

    @JsonProperty("attributes_to_retrieve")
    public List<String> attributesToRetrieve;

    @JsonProperty("records_offset")
    public Object recordsOffset;



    public void setBrandFilter(String brandValues) {
        List<Filter> filters = new ArrayList<>();
        Filter brandFilter = new Filter();
        brandFilter.setFieldName("brand_string_mv");

        if (brandValues != null && !brandValues.isEmpty()) {
             brandFilter.setValues(Collections.singletonList(brandValues));
        } else {
            throw new IllegalArgumentException("Brand values cannot be null or empty");

        }
        this.filters = filters;
        filters.add(brandFilter);
    }

    public void setColorFilter(String colorValues) {
        List<Filter> filters = new ArrayList<>();
        Filter colorFilter = new Filter();
        colorFilter.setFieldName("verticalcolorfamily_en_string_mv");

        if (colorValues != null && !colorValues.isEmpty()) {
            colorFilter.setValues(Collections.singletonList(colorValues));
        } else {
            throw new IllegalArgumentException("Color values cannot be null or empty");
        }

        filters.add(colorFilter);
        this.filters = filters;
    }

    public void setPriceRangeFilter(String fieldName, String priceRange) {
        List<Filter> filters = new ArrayList<>();
        Filter priceFilter = new Filter();
        priceFilter.setFieldName(fieldName);
        priceFilter.setValues(Collections.singletonList(priceRange));
        filters.add(priceFilter);
        this.filters = filters;
    }

    public void setDiscountFilter(String fieldName, String discountRange) {
        List<Filter> filters = new ArrayList<>();
        Filter discountFilter = new Filter();
        discountFilter.setFieldName(fieldName);
        discountFilter.setValues(Collections.singletonList(discountRange));
        filters.add(discountFilter);
        this.filters = filters;
    }

    public void setGenderFilter(String fieldName, String gender) {
        List<Filter> filters = new ArrayList<>();
        Filter genderFilter = new Filter();
        genderFilter.setFieldName(fieldName);
        genderFilter.setValues(Collections.singletonList(gender));
        filters.add(genderFilter);
        this.filters = filters;
    }

    public void setL1L3CategoryFilter(String fieldName, String category) {
        List<Filter> filters = new ArrayList<>();
        Filter l1l3Filter = new Filter();
        l1l3Filter.setFieldName(fieldName);
        l1l3Filter.setValues(Collections.singletonList(category));
        filters.add(l1l3Filter);
        this.filters = filters;
    }

    public void setSizeFilter(String fieldName, String size) {
        List<Filter> filters = new ArrayList<>();
        Filter sizeFilter = new Filter();
        sizeFilter.setFieldName(fieldName);
        sizeFilter.setValues(Collections.singletonList(size));
        filters.add(sizeFilter);
        this.filters = filters;
    }

    public void setRecordsOffset(String value) {
        try {
            this.recordsOffset = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.recordsOffset = value;
        }
    }

    public Object getRecordsOffset() {
        return recordsOffset;
    }

    public void setDisableFacets(boolean disableFacets) {
        this.disableFacets = disableFacets;
    }

    public boolean isDisableFacets() {
        return disableFacets;
    }

    public void setEnableFacetCounts(boolean enableFacetValuesCount) {
        this.enableFacetValuesCount = enableFacetValuesCount;
    }

    public boolean isEnableFacetCounts() {
        return enableFacetValuesCount;
    }

    public void setAttributesToRetrieve(List<String> attributesToRetrieve) {
        this.attributesToRetrieve = attributesToRetrieve;
    }

    public List<String> getAttributesToRetrieve() {
        return attributesToRetrieve;
    }

    public void setPageNumber(Object pageNumber) {
        this.pageNumber = pageNumber;
    }

//    public Object getPageNumber() {
//        return pageNumber;
//    }

    public void setPageNumberValue(String value) {
        try {
            this.pageNumber = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.pageNumber = value;
        }
    }


    public static class Filter {

        @JsonProperty("fieldName")
        public String fieldName;

        @JsonProperty("values")
        public List<String> values;

     //   public String operator;

//        public void setOperator(String operator) {
//            this.operator = operator;
//        }
//
//        public String getOperator() {
//            return operator;
//        }
        // Default constructor
        public Filter() {}

        // Constructor from Map
//        public Filter(Map<String, Object> map) {
//            if (map != null) {
//                this.fieldName = (String) map.get("fieldName");
//                Object valuesObj = map.get("values");
//                if (valuesObj instanceof List) {
//                    this.values = new ArrayList<>((List<String>) valuesObj);
//                } else if (valuesObj instanceof String) {
//                    this.values = Collections.singletonList((String) valuesObj);
//                }
//            }
//        }
//        @SuppressWarnings("unchecked")
//        public static Filter fromMap(Object obj) {
//            if (obj instanceof Map) {
//                Map<String, Object> map = (Map<String, Object>) obj;
//                Filter filter = new Filter();
//                filter.fieldName = (String) map.get("fieldName");
//
//                Object valuesObj = map.get("values");
//                if (valuesObj instanceof List) {
//                    filter.values = new ArrayList<>((List<String>) valuesObj);
//                } else if (valuesObj instanceof String) {
//                    filter.values = Collections.singletonList((String) valuesObj);
//                }
//                return filter;
//            }
//            return null;
//        }

        @JsonAnySetter
        public void handleUnknown(String key, Object value) {
            if ("fieldName".equals(key)) {
                this.fieldName = (String) value;
            } else if ("values".equals(key)) {
                if (value instanceof List) {
                    this.values = (List<String>) value;
                } else if (value instanceof String) {
                    this.values = Collections.singletonList((String) value);
                }
            }
        }

        // Getters and setters
        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

//        public List<String> getValues() {
//            return values;
//        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        // Convert to Map
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("fieldName", fieldName);
            map.put("values", values);
            return map;
        }

        private Map<String, Object> filterMap;

//        public Filter() {
//            this.filterMap = new HashMap<>();
//        }

        public Filter(String fieldName, List<String> values) {
            this.fieldName = fieldName;
            this.values = values;
            this.filterMap = new HashMap<>();
            this.filterMap.put("fieldName", fieldName);
            this.filterMap.put("values", values);
        }

        public Map<String, Object> getFilterMap() {
            return filterMap;
        }

        public void setFilterMap(Map<String, Object> filterMap) {
            this.filterMap = filterMap;
        }

        // New getters and setters
//        public String getFieldName() {
//            return fieldName;
//        }

//        public void setFieldName(String fieldName) {
//            this.fieldName = fieldName;
//            this.filterMap.put("fieldName", fieldName);
//        }

        public List<String> getValues() {
            return values;
        }

//        public void setValues(List<String> values) {
//            this.values = values;
//            this.filterMap.put("values", values);
//        }


//        public Filter(String fieldName, List<String> values) {
//            this.fieldName = fieldName;
//            this.values = values;
//        }
        // Add setter for fieldName
//        public void setFieldName(String fieldName) {
//            this.fieldName = fieldName;
//            this.filterMap.put("fieldName", fieldName);
//        }

        // Add getter for fieldName
//        public String getFieldName() {
//            return fieldName;
//        }

        // Existing setters and getters for values
//        public void setValues(List<String> values) {
//            this.values = values;
//            this.filterMap.put("values", values);
//        }

//        public List<String> getValues() {
//            return values;
//        }

    }

    public List<Filter> getFilters() {
        return filters;
    }

//    public void setFilters(List<Filter> filters) {
//        this.filters = filters;
//    }
@SuppressWarnings("unchecked")
public void setFilters(List<Filter> filters) {
    this.filters = new ArrayList<>(filters);
//    if (filters == null) {
//        this.filters = null;
//        return;
    }


//    this.filters = filters.stream()
//            .map(Filter::fromMap)
//            .filter(Objects::nonNull)
//            .collect(Collectors.toList());
//    ObjectMapper mapper = new ObjectMapper();
//    this.filters = filters.stream()
//            .map(filter -> mapper.convertValue(filter, Filter.class))
//            .collect(Collectors.toList());
}




