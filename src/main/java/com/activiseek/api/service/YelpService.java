package com.activiseek.api.service;

import com.activiseek.api.config.ApplicationProperties;
import com.activiseek.api.domain.yelp.Business;
import com.activiseek.api.domain.yelp.BusinessList;
import com.activiseek.api.domain.yelp.BusinessReviews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class YelpService {
    private final Logger log = LoggerFactory.getLogger(YelpService.class);

    private RestTemplate restTemplate;

    private HttpHeaders headers;
    private HttpEntity httpEntityHeaders;
    private Map<String, String> uriVariables;
    private String apiKey;
    private String basePath;

    public YelpService(RestTemplate restTemplate, ApplicationProperties properties) {
        this.restTemplate = restTemplate;
        this.apiKey = properties.getYelpApi().getApiKey();
        this.basePath = properties.getYelpApi().getBasePath();

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        headers.add("Authorization", "Bearer " + apiKey);

        httpEntityHeaders = new HttpEntity(headers);
        uriVariables = new HashMap<>();
    }

    public Business getBusiness(String businessId) {
        uriVariables.clear();
        uriVariables.put("id", businessId);

        ResponseEntity<Business> response = restTemplate.exchange(
            basePath + "/businesses/{id}",
            HttpMethod.GET,
            httpEntityHeaders,
            Business.class,
            uriVariables
        );
        log.debug("Response: " + response);
        return response.getBody();
    }

    public BusinessList searchBusiness(Map<String, Optional<String>> parameters) {
        StringBuilder url = new StringBuilder(basePath + "/businesses/search?");
        parameters.forEach((key,value) -> {
            value.ifPresent(v -> url.append("&").append(key).append("=").append(v));
        });

        ResponseEntity<BusinessList> response = restTemplate.exchange(
            url.toString(),
            HttpMethod.GET,
            httpEntityHeaders,
            BusinessList.class
        );
        log.info("Response: " + response);
        return response.getBody();
    }

    public BusinessReviews getBusinessReviews(String businessId) {
        uriVariables.clear();
        uriVariables.put("id", businessId);

        ResponseEntity<BusinessReviews> response = restTemplate.exchange(
            basePath + "/businesses/{id}/reviews",
            HttpMethod.GET,
            httpEntityHeaders,
            BusinessReviews.class,
            uriVariables
        );
        return response.getBody();
    }
}
