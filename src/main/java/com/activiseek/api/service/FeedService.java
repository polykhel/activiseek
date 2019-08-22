package com.activiseek.api.service;

import com.activiseek.api.domain.yelp.BusinessList;
import com.activiseek.api.web.api.FeedApiDelegate;
import com.activiseek.api.web.api.model.Business;
import com.activiseek.api.web.api.model.BusinessLocation;
import com.activiseek.api.web.api.model.Card;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService implements FeedApiDelegate {

    private YelpService yelpService;

    public FeedService(YelpService yelpService) {
        this.yelpService = yelpService;
    }

    @Override
    public ResponseEntity<List<Card>> getFeed(String term, String location,
                                              String latitude, String longitude,
                                              String categories, String price, String attributes) {
        Map<String, Optional<String>> queryParams = new HashMap<>();
        queryParams.put("term", Optional.ofNullable(term));
        queryParams.put("location", Optional.ofNullable(location));
        queryParams.put("latitude", Optional.ofNullable(latitude));
        queryParams.put("longitude", Optional.ofNullable(longitude));
        queryParams.put("categories", Optional.ofNullable(categories));
        queryParams.put("price", Optional.ofNullable(price));
        queryParams.put("attributes", Optional.ofNullable(attributes));
        BusinessList businessList = yelpService.searchBusiness(queryParams);
        return new ResponseEntity<>(businessList.getBusinesses().stream()
            .map(b -> {
                Card card = new Card();
                card.setId(b.getId());
                Business business = new Business();
                business.setId(b.getAlias());
                business.setName(b.getName());
                business.setImageUrl(b.getImageUrl());
                business.setPriceRange(b.getPrice());
                business.setReviewCount(b.getReviewCount());
                business.setUrl(b.getUrl());
                business.setUserRating(b.getRating());
                BusinessLocation businessLocation = new BusinessLocation();
                businessLocation.setAddress(b.getLocation().getAddress1() + " " +
                    b.getLocation().getAddress2() + b.getLocation().getAddress3());
                businessLocation.setCity(b.getLocation().getCity());
                businessLocation.setCountry(b.getLocation().getCountry());
                businessLocation.setLatitude(b.getCoordinates().getLatitude());
                businessLocation.setLongitude(b.getCoordinates().getLongitude());
                businessLocation.setState(b.getLocation().getState());
                businessLocation.setZipcode(b.getLocation().getZipCode());
                business.setLocation(businessLocation);
                card.setBusiness(business);
                return card;
            }).collect(Collectors.toList()), HttpStatus.OK);
    }
}
