package com.esoft.state;

public class CompletedState implements OrderState {
    @Override
    public void next(OrderStateContext context) {
        System.out.println("Order is already completed. No further states.");
    }
}
