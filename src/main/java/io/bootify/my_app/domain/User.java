package io.bootify.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "longtext")
    private String address;

    @Column(length = 15)
    private String phone;

    @Column
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Order> userOrders;

    @OneToMany(mappedBy = "user")
    private Set<Review> userReviews;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(final Set<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public Set<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(final Set<Review> userReviews) {
        this.userReviews = userReviews;
    }

}
