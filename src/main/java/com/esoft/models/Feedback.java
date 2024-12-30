package com.esoft.models;

public class Feedback {
    private int rating; // Rating out of 5
    private String comment;

    public Feedback(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + "/5, Comment: " + comment;
    }
}
