package com.application.ecommerce.service;//package com.application.ecommerce.service;

import com.application.ecommerce.model.Listing;

import java.util.List;

public interface ListingService {

    public Listing createListing(Listing list);

    public Listing getListing(String listingId);

    public List<Listing> getAllListings();

    public Listing updateListing(Listing list);

    public boolean deleteListing(String listingId);
}
