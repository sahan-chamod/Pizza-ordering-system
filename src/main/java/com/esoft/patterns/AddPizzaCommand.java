package com.esoft.patterns;

import com.esoft.models.Order;
import com.esoft.services.AdminService;

public class AddPizzaCommand implements Command {
    private AdminService adminService;

    public AddPizzaCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute(Order order) {
        adminService.addPizza();
    }
}
