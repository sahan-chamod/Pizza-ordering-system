package com.esoft.state;

public class ProcessingState implements OrderState {
    @Override
    public void next(OrderStateContext context) {
        System.out.println("Order moved to Completed state.");
        context.setState(new CompletedState());
    }
}
