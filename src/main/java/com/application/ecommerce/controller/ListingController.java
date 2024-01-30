package com.application.ecommerce.controller;

import com.application.ecommerce.model.Listing;
import com.application.ecommerce.model.custom.Constant;
import com.application.ecommerce.model.custom.CustomErrorResponse;
import com.application.ecommerce.model.custom.Log;
import com.application.ecommerce.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @PostMapping("/create")
    public ResponseEntity<Object> createListing(@RequestBody @Valid Listing listing) {
        Log.LOGGER.info(Constant.CREATE_LISTING_REQUEST);
        try {
            Listing list = listingService.createListing(listing);
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllListings() {
        Log.LOGGER.info(Constant.GET_ALL_LISTING_REQUEST);
        try {
            List<Listing> listingList = listingService.getAllListings();
            if (!listingList.isEmpty()) {
                return new ResponseEntity<>(listingList, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.LISTINGS_NOT_FOUND);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateListing(@RequestBody @Valid Listing listing) {
        Log.LOGGER.info(Constant.UPDATE_LISTING_REQUEST);
        try {
            Listing list = listingService.updateListing(listing);
            if (list != null) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.LISTING_NOT_FOUND);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListing")
    public ResponseEntity<Object> getListing(@RequestParam String listingId) {
        Log.LOGGER.info(Constant.GET_LISTING_REQUEST);
        try {
            Listing listing = listingService.getListing(listingId);
            return new ResponseEntity<>(listing, HttpStatus.FOUND);
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteListing(@RequestParam String listingId) {
        Log.LOGGER.info(Constant.DELETE_LISTING_REQUEST);
        try {
            if (listingService.deleteListing(listingId)) {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.DELETE_LISTING_SUCCESS + listingId);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.DELETE_LISTING_ERROR + listingId);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
