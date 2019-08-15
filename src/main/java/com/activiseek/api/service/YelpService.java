package com.activiseek.api.service;

import com.activiseek.api.config.ApplicationProperties;
import com.activiseek.api.domain.yelp.Business;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class YelpService {
    private final Logger log = LoggerFactory.getLogger(YelpService.class);

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    private HttpHeaders headers;
    private HttpEntity httpEntityHeaders;
    private Map<String, String> uriVariables;
    private String apiKey;
    private String basePath;

    public YelpService(ObjectMapper objectMapper, RestTemplate restTemplate, ApplicationProperties properties) {
        this.objectMapper = objectMapper;
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

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

        ResponseEntity<String> response = restTemplate.exchange(
            basePath + "/businesses/{id}",
            HttpMethod.GET,
            httpEntityHeaders,
            String.class,
            uriVariables
        );
        log.info("Response: " + response);
        try {
            Business business = objectMapper.readValue(response.getBody(), Business.class);
            String id = business.getId();
            String alias = business.getAlias();
            log.info("id and alias: " + id + ", " + alias);
            return business;
        } catch (Exception ignored) {
        }
        return null;
    }
}
