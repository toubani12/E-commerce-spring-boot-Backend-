package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Category;
import io.bootify.my_app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findFirstByCategory(Category category);

}
