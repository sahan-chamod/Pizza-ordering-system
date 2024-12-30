package com.esoft.commands;

import com.esoft.models.Order;
import com.esoft.services.OrderService;

public class CreateOrderCommand implements OrderCommand {
    private OrderService orderService;

    public CreateOrderCommand(OrderService orderService){
        this.orderService = orderService;
    }
    @Override
    public void execute(Order order) {
        orderService.createOrder(order);
    }

}
