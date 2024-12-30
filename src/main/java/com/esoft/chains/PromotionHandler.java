package com.esoft.chains;

import java.math.BigDecimal;

public interface PromotionHandler {
    BigDecimal applyPromotion(BigDecimal totalPrice);
    void setNextHandler(PromotionHandler nextHandler);
}
