package com.esoft.services;

import com.esoft.config.DBConnection;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class LoyaltyPointsService {
    private static final int POINTS_PER_DOLLAR = 10;

    public int calculatePoints(@NotNull BigDecimal totalPrice) {
        return totalPrice.multiply(BigDecimal.valueOf(POINTS_PER_DOLLAR)).intValue();
    }

    public void addLoyaltyPoints(String username, String orderId, BigDecimal totalPrice) {
        int pointsEarned = calculatePoints(totalPrice);

        String insertQuery = "INSERT INTO loyalty_points (user_id, points, last_updated) VALUES (?, ?, NOW())";
        String [] params = {username,String.valueOf(pointsEarned)};
        DBConnection.executeUpdate(insertQuery,params);
        DBConnection.closeConnectio();
    }
}
