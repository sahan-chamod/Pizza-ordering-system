package com.esoft.chains;

import java.math.BigDecimal;

public abstract class AbstractPromotionHandler implements PromotionHandler {
    protected PromotionHandler nextHandler;

    @Override
    public void setNextHandler(PromotionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected BigDecimal applyNext(BigDecimal totalPrice) {
        if (nextHandler != null) {
            return nextHandler.applyPromotion(totalPrice);
        }
        return totalPrice; // If there's no next handler, return the unchanged total price.
    }
}
