package com.activiseek.api.web.rest;

import com.activiseek.api.domain.yelp.Business;
import com.activiseek.api.domain.yelp.BusinessList;
import com.activiseek.api.domain.yelp.BusinessReviews;
import com.activiseek.api.service.YelpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FeedResource {

    private final Logger log = LoggerFactory.getLogger(FeedResource.class);

    private final YelpService yelpService;

    public FeedResource(YelpService yelpService) {
        this.yelpService = yelpService;
    }

    @GetMapping("/biz/{businessId}")
    public Business getBusiness(@PathVariable String businessId) {
        return yelpService.getBusiness(businessId);
    }

    @GetMapping("/biz/search")
    public BusinessList searchBusiness(@RequestParam(required = false) String term,
                                       @RequestParam(required = false) String location) {
        Map<String, String> queryParams = new HashMap<>();
        if (term != null) {
            queryParams.put("term", term);
        }
        if (location != null) {
            queryParams.put("location", location);
        }
        return yelpService.searchBusiness(queryParams);
    }

    @GetMapping("/biz/{businessId}/reviews")
    public BusinessReviews getBusinessReviews(@PathVariable String businessId) {
        return yelpService.getBusinessReviews(businessId);
    }
}
