package com.activiseek.api.service;

import com.activiseek.api.domain.yelp.Business;
import com.activiseek.api.domain.yelp.Category;
import com.activiseek.api.domain.yelp.OpeningHours;
import com.activiseek.api.web.api.CardApiDelegate;
import com.activiseek.api.web.api.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardService implements CardApiDelegate {

    private YelpService yelpService;

    public CardService(YelpService yelpService) {
        this.yelpService = yelpService;
    }

    @Override
    public ResponseEntity<BusinessDetails> getCardBusinessDetails(String cardId) {
        Business business = yelpService.getBusiness(cardId);
        BusinessDetails businessDetails = new BusinessDetails();
        businessDetails.setId(business.getAlias());
        businessDetails.setName(business.getName());
        businessDetails.setCategories(business.getCategories().stream().map(Category::getTitle).collect(Collectors.toList()));
        businessDetails.setHasOnlineDelivery(business.getTransactions().contains("delivery"));
        businessDetails.setHasPickupService(business.getTransactions().contains("pickup"));
        businessDetails.setHasTableBooking(business.getTransactions().contains("restaurant_reservation"));
        BusinessHours hours = new BusinessHours();
        business.getHours().forEach(hour -> {
            hours.setIsOpenNow(hour.isOpenNow());
            hour.getOpen().forEach(openingHours -> {
                BusinessHoursOpen open = new BusinessHoursOpen();
                open.setDay(openingHours.getDay());
                open.setEnd(openingHours.getEnd());
                open.setStart(openingHours.getStart());
                hours.addOpenItem(open);
            });
        });
        businessDetails.setHours(hours);
        businessDetails.setImageUrl(business.getImageUrl());
        BusinessLocation businessLocation = new BusinessLocation();
        businessLocation.setAddress(business.getLocation().getAddress1() + " " +
            business.getLocation().getAddress2() + business.getLocation().getAddress3());
        businessLocation.setCity(business.getLocation().getCity());
        businessLocation.setCountry(business.getLocation().getCountry());
        businessLocation.setLatitude(business.getCoordinates().getLatitude());
        businessLocation.setLongitude(business.getCoordinates().getLongitude());
        businessLocation.setState(business.getLocation().getState());
        businessLocation.setZipcode(business.getLocation().getZipCode());
        businessDetails.setLocation(businessLocation);
        businessDetails.setPhone(business.getPhone());
        businessDetails.setPhotos(business.getPhotos());
        businessDetails.setPriceRange(business.getPrice());
        businessDetails.setReviewCount(business.getReviewCount());
        businessDetails.setUrl(business.getUrl());
        return new ResponseEntity<>(businessDetails, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BusinessReview>> getCardBusinessReviews(String cardId) {
        return null;
    }
}
