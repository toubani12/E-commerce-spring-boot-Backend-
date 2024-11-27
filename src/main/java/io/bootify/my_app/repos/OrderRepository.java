package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Order;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);

}
