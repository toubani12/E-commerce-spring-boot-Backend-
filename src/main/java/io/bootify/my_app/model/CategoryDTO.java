package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CategoryDTO {

    private Integer categoryId;

    @NotNull
    @Size(max = 255)
    private String categoryName;

    private String categoryDescription;

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

}
