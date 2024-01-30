package com.application.ecommerce.model.custom;

public class Constant {

    public static final String CREATE_USER_REQUEST = "Received request for api/user/createUser";

    public static final String GET_USER_REQUEST = "Received request for api/user/getUser/";

    public static final String USER_DELETED_SUCCESS = "User deleted successfully ";

    public static final String DELETE_USER_REQUEST = "Received request for api/user/delete";

    public static final String UPDATE_USER_REQUEST = "Received request for api/user/update";

    public static final String DELETE_USER_ERROR = "No user found for deleting with email = ";

    public static final String CREATE_LISTING_REQUEST = "Received request for api/listing/create";

    public static final String GET_ALL_LISTING_REQUEST = "Received request for api/listings/all";

    public static final String START_SCHEDULER = "Running scheduler as per the scheduled time ";

    public static final String UPDATE_LISTING_REQUEST = "Received request for api/listing/update";

    public static final String GET_LISTING_REQUEST = "Received request for api/listing/getListing";

    public static final String DELETE_LISTING_REQUEST = "Received request for api/listing/delete";

    public static final String DELETE_LISTING_SUCCESS = "Successfully deleted listing with listingId : ";
    public static final String DELETE_LISTING_ERROR = "No listing found with listingId : ";

    public static final String CART_NOT_FOUND = "Cart empty for : ";

    public static final String LISTING_NOT_FOUND = "No Listing found";

    public static final String LISTINGS_NOT_FOUND = "No Listings found";

    public static final String DELETE_CART_ERROR = "item not found to delete";

    public static final String CLEAR_CART_SUCCESS = "Cleared cart for : ";

    public static final String CLEAR_CART_FAILED = "Cart is empty for : ";

    public static final String ADD_TO_CART_REQUEST = "Received request for api/cart/addToCart";

    public static final String GET_CART_REQUEST = "Received request for api/cart/getCart";

    public static final String DELETE_ITEM_REQUEST = "Received request for api/cart/delete";

    public static final String CLEAR_CART_REQUEST = "Received request for api/cart/clear";

    public static final String SCHEDULER_CLEARED = "Scheduler cleared cart for : ";

    public static final String NO_CARTS = "No carts found";

    public static final String CREATE_ORDER_ERROR = "Cart is empty for : ";

    public static final String GET_ALL_CARTS_REQUEST = "Received request for api/cart/all";

    public static final String CREATE_ORDER_REQUEST = "Received request for api/order/create";

    public static final String GET_ORDER_REQUEST = "Received request for api/order/getOrder";

    public static final String GET_ORDER_ERROR = "No order present with order Id : ";

    public static final String GET_ORDERS_BY_EMAIL_REQUEST = "Received request for api/order/getOrdersByEmail";

    public static final String GET_ORDERS_BY_EMAIL_ERROR = "No orders to display for the email : ";

    public static final String PATCH_ORDER_REQUEST = "Received request for api/order/patch";

    public static final String PATCH_ORDER_ERROR = "No order found with orderId : ";

    public static final String ALL_ORDER_ERROR = "No orders available";

    public static final String ALL_ORDER_REQUEST = "Received request for api/order/all";

    public static final String ALL_ORDER_INVALID_ENUM = "please provide valid orderStatus param";
}
