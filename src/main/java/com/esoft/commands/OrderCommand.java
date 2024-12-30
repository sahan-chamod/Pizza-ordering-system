package com.esoft.commands;

import com.esoft.models.Order;

public interface OrderCommand {
    void execute(Order order);
}
