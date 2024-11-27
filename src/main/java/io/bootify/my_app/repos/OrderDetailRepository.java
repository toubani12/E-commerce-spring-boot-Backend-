package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Order;
import io.bootify.my_app.domain.OrderDetail;
import io.bootify.my_app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    OrderDetail findFirstByOrder(Order order);

    OrderDetail findFirstByProduct(Product product);

}
