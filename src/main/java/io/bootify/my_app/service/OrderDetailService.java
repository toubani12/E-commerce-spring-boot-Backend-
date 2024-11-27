package io.bootify.my_app.service;

import io.bootify.my_app.domain.Order;
import io.bootify.my_app.domain.OrderDetail;
import io.bootify.my_app.domain.Product;
import io.bootify.my_app.model.OrderDetailDTO;
import io.bootify.my_app.repos.OrderDetailRepository;
import io.bootify.my_app.repos.OrderRepository;
import io.bootify.my_app.repos.ProductRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailService(final OrderDetailRepository orderDetailRepository,
            final OrderRepository orderRepository, final ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDetailDTO> findAll() {
        final List<OrderDetail> orderDetails = orderDetailRepository.findAll(Sort.by("orderDetailId"));
        return orderDetails.stream()
                .map(orderDetail -> mapToDTO(orderDetail, new OrderDetailDTO()))
                .toList();
    }

    public OrderDetailDTO get(final Integer orderDetailId) {
        return orderDetailRepository.findById(orderDetailId)
                .map(orderDetail -> mapToDTO(orderDetail, new OrderDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDetailDTO orderDetailDTO) {
        final OrderDetail orderDetail = new OrderDetail();
        mapToEntity(orderDetailDTO, orderDetail);
        return orderDetailRepository.save(orderDetail).getOrderDetailId();
    }

    public void update(final Integer orderDetailId, final OrderDetailDTO orderDetailDTO) {
        final OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDetailDTO, orderDetail);
        orderDetailRepository.save(orderDetail);
    }

    public void delete(final Integer orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    private OrderDetailDTO mapToDTO(final OrderDetail orderDetail,
            final OrderDetailDTO orderDetailDTO) {
        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setPricePerUnit(orderDetail.getPricePerUnit());
        orderDetailDTO.setOrder(orderDetail.getOrder() == null ? null : orderDetail.getOrder().getOrderId());
        orderDetailDTO.setProduct(orderDetail.getProduct() == null ? null : orderDetail.getProduct().getProductId());
        return orderDetailDTO;
    }

    private OrderDetail mapToEntity(final OrderDetailDTO orderDetailDTO,
            final OrderDetail orderDetail) {
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPricePerUnit(orderDetailDTO.getPricePerUnit());
        final Order order = orderDetailDTO.getOrder() == null ? null : orderRepository.findById(orderDetailDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderDetail.setOrder(order);
        final Product product = orderDetailDTO.getProduct() == null ? null : productRepository.findById(orderDetailDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        orderDetail.setProduct(product);
        return orderDetail;
    }

}
