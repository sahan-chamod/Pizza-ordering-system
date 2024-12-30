package com.esoft.commands;

import com.esoft.models.Order;
import com.esoft.services.OrderService;

public class CancelOrderCommand implements OrderCommand{
    private OrderService orderService;
    public CancelOrderCommand(OrderService orderService){
        this.orderService = orderService;
    }
    @Override
    public void execute(Order order) {
        this.orderService.canselOrder(order);
    }
}
