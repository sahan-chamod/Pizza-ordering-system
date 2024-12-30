package com.esoft.patterns;

import com.esoft.models.Order;

public interface Command {
    void execute(Order order);
}
