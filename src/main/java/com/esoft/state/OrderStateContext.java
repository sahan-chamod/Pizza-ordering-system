package com.esoft.state;

import com.esoft.models.Order;

public class OrderStateContext {
    private Order order;
    private OrderState state;

    public OrderStateContext(Order order) {
        this.order = order;
        this.state = new NewOrderState();
    }

    public void nextState() {
        state.next(this);
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Order getOrder() {
        return order;
    }
}
