package com.esoft.services;

import com.esoft.models.Feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackService {
    private Map<Integer, List<Feedback>> feedbackMap = new HashMap<>();

    public void addFeedback(int pizzaId, Feedback feedback) {
        feedbackMap.putIfAbsent(pizzaId, new ArrayList<>());
        feedbackMap.get(pizzaId).add(feedback);
    }

    public void displayAllFeedback() {
        if (feedbackMap.isEmpty()) {
            System.out.println("No feedbacks available.");
            return;
        }

        for (Map.Entry<Integer, List<Feedback>> entry : feedbackMap.entrySet()) {
            System.out.println("Pizza ID: " + entry.getKey());
            for (Feedback feedback : entry.getValue()) {
                System.out.println("  " + feedback);
            }
        }
    }

    public List<Feedback> getFeedbackForPizza(int pizzaId) {
        return feedbackMap.getOrDefault(pizzaId, new ArrayList<>());
    }
}
