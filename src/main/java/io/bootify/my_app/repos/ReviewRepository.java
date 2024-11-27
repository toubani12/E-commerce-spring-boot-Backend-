package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Product;
import io.bootify.my_app.domain.Review;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findFirstByProduct(Product product);

    Review findFirstByUser(User user);

}
