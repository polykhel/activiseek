package com.activiseek.api.web.rest;

import com.activiseek.api.service.YelpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FeedResource {

    private final Logger log = LoggerFactory.getLogger(FeedResource.class);

    private final YelpService yelpService;

    public FeedResource(YelpService yelpService) {
        this.yelpService = yelpService;
    }

    @GetMapping("/biz")
    public String getBusiness() throws Exception {
        yelpService.getBusiness();
        return "hello";
    }
}
