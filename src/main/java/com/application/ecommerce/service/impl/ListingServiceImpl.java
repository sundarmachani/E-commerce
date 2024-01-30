package com.application.ecommerce.service.impl;//package com.application.ecommerce.service.impl;

import com.application.ecommerce.model.Listing;
import com.application.ecommerce.model.eNum.ProductTypeEnum;
import com.application.ecommerce.repository.ListingRepository;
import com.application.ecommerce.service.ListingService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Listing createListing(Listing list) {
        if (list.getListingId() != null) {
            throw new RuntimeException("listingId shouldn't be sent from payload");
        }
        List<Listing> listingList = getAllListings();
        listingList.stream().filter(f -> f.getTitle().equalsIgnoreCase(list.getTitle())).findFirst().ifPresent(f -> {
            throw new RuntimeException("Listing already present with '" + list.getTitle() + "'");
        });
        if (list.getType().equals(ProductTypeEnum.ELECTRONICS)) {
            list.setListingId("EL-" + listIdGenerator());
        } else if (list.getType().equals(ProductTypeEnum.ESSENTIALS)) {
            list.setListingId("ES-" + listIdGenerator());
        } else if (list.getType().equals(ProductTypeEnum.SPORTS)) {
            list.setListingId("SP-" + listIdGenerator());
        } else if (list.getType().equals(ProductTypeEnum.FOOD_AND_BEVERAGE)) {
            list.setListingId("FAB-" + listIdGenerator());
        }
        return listingRepository.insert(list);
    }

    private String listIdGenerator() {
        Random random = new Random();
        int digits = 1000 + random.nextInt(9000);
        return Integer.toString(digits);
    }

    @Override
    public Listing getListing(String listingId) {
        Optional<Listing> optionalListing = listingRepository.findById(listingId);
        if (optionalListing.isPresent()) {
            return optionalListing.get();
        } else {
            throw new RuntimeException("Listing with " + listingId + " is not found");
        }
    }

    @Override
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    @Override
    public Listing updateListing(Listing list) {
        if (list.getListingId() == null) {
            throw new RuntimeException("listingId should be sent from payload");
        }
        if (listingRepository.existsById(list.getListingId())) {
            return listingRepository.save(list);
        } else {
            throw new RuntimeException("No listing found with " + list.getListingId() + " to update");
        }
    }

    @Override
    public boolean deleteListing(String listingId) {
        if (listingRepository.existsById(listingId)) {
            listingRepository.deleteById(listingId);
            return true;
        } else {
            return false;
        }
    }
}
