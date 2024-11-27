package io.bootify.my_app.service;

import io.bootify.my_app.domain.Order;
import io.bootify.my_app.domain.OrderDetail;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.model.OrderDTO;
import io.bootify.my_app.repos.OrderDetailRepository;
import io.bootify.my_app.repos.OrderRepository;
import io.bootify.my_app.repos.UserRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
            final OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("orderId"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Integer orderId) {
        return orderRepository.findById(orderId)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getOrderId();
    }

    public void update(final Integer orderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getUserId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus());
        final User user = orderDTO.getUser() == null ? null : userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        return order;
    }

    public ReferencedWarning getReferencedWarning(final Integer orderId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        final OrderDetail orderOrderDetail = orderDetailRepository.findFirstByOrder(order);
        if (orderOrderDetail != null) {
            referencedWarning.setKey("order.orderDetail.order.referenced");
            referencedWarning.addParam(orderOrderDetail.getOrderDetailId());
            return referencedWarning;
        }
        return null;
    }

}
