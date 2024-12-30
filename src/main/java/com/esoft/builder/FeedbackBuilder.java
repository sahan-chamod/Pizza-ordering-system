package com.esoft.builder;

import com.esoft.models.Feedback;

public class FeedbackBuilder {
    private int rating;
    private String comment;

    public FeedbackBuilder setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public FeedbackBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Feedback build() {
        return new Feedback(rating, comment);
    }
}
