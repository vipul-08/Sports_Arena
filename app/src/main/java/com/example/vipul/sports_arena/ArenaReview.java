package com.example.vipul.sports_arena;

/**
 * Created by vipul on 4/12/17.
 */

public class ArenaReview {

    private String reviewBy,reviewText,reviewRating;


    public ArenaReview(String reviewBy, String reviewText, String reviewRating) {
        this.reviewBy = reviewBy;
        this.reviewText = reviewText;
        this.reviewRating = reviewRating;
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }
}
