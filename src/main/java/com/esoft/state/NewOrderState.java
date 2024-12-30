package com.esoft.state;

public class NewOrderState implements OrderState {
    @Override
    public void next(OrderStateContext context) {
        System.out.println("Order moved to Processing state.");
        context.setState(new ProcessingState());
    }
}
