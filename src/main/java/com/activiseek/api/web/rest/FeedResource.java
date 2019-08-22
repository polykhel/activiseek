package com.activiseek.api.web.rest;

import com.activiseek.api.domain.yelp.Business;
import com.activiseek.api.domain.yelp.BusinessList;
import com.activiseek.api.domain.yelp.BusinessReviews;
import com.activiseek.api.service.FeedService;
import com.activiseek.api.service.YelpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FeedResource {

    private final Logger log = LoggerFactory.getLogger(FeedResource.class);

    private final YelpService yelpService;

    private final FeedService feedService;

    public FeedResource(YelpService yelpService, FeedService feedService) {
        this.feedService = feedService;
        this.yelpService = yelpService;
    }

    @GetMapping("/biz/{businessId}")
    public Business getBusiness(@PathVariable String businessId) {
        return yelpService.getBusiness(businessId);
    }

    @GetMapping("/biz/search")
    public BusinessList searchBusiness(@RequestParam(required = false) String term,
                                       @RequestParam(required = false) String location) {

        feedService.getFeed("food", "manila", null, null, null, null, null);
        return null;
    }

    @GetMapping("/biz/{businessId}/reviews")
    public BusinessReviews getBusinessReviews(@PathVariable String businessId) {
        return yelpService.getBusinessReviews(businessId);
    }
}
