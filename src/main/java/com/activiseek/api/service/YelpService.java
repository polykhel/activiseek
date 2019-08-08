package com.activiseek.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class YelpService {
    private final Logger log = LoggerFactory.getLogger(YelpService.class);

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${application.yelpApi.apiKey}")
    private String apiKey;

    @Value("${application.yelpApi.basePath}")
    private String basePath;

    public YelpService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void getBusiness() throws Exception {
        Map<String, String> uriVariables = new HashMap();
        uriVariables.put("id", "GTrblWr4tWS7laa-N9sEJQ");
        ResponseEntity<String> response
            = restTemplate.getForEntity(basePath + "/businesses/{id}", String.class, uriVariables);
        log.info("Response: " + response);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        String id = jsonNode.get("id").asText();
        String alias = jsonNode.get("alias").asText();
        log.info("id and alias: " + id + ", " + alias);
    }
}
