package com.alikorkmaz.shoppingcart.exception;

public class DiscountNotApplicableException extends RuntimeException {

    public DiscountNotApplicableException(String discountType) {
        super(String.format("discount not applicable for %s", discountType));
    }
}
