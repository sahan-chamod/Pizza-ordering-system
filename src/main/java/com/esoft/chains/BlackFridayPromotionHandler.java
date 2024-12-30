package com.esoft.chains;

import java.math.BigDecimal;

public class BlackFridayPromotionHandler extends AbstractPromotionHandler {

    @Override
    public BigDecimal applyPromotion(BigDecimal totalPrice) {
        BigDecimal discountRate = BigDecimal.valueOf(0.10); // 10% discount
        BigDecimal discount = totalPrice.multiply(discountRate);
        BigDecimal discountedPrice = totalPrice.subtract(discount);

        // Pass the discounted price to the next handler in the chain
        return applyNext(discountedPrice);
    }
}
