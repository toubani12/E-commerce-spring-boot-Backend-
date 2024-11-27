package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;


public class ReviewDTO {

    private Integer reviewId;

    @NotNull
    private Boolean rating;

    private String reviewText;

    private OffsetDateTime createdAt;

    @NotNull
    private Integer product;

    @NotNull
    private Integer user;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(final Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(final Boolean rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(final String reviewText) {
        this.reviewText = reviewText;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(final Integer product) {
        this.product = product;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(final Integer user) {
        this.user = user;
    }

}
