package io.bootify.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class Category {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column(columnDefinition = "longtext")
    private String categoryDescription;

    @OneToMany(mappedBy = "category")
    private Set<Product> categoryProducts;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(final String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Set<Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(final Set<Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }

}
