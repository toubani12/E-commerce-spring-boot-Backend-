package io.bootify.my_app.rest;

import io.bootify.my_app.model.OrderDetailDTO;
import io.bootify.my_app.service.OrderDetailService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/orderDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderDetailResource {

    private final OrderDetailService orderDetailService;

    public OrderDetailResource(final OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(
            @PathVariable(name = "orderDetailId") final Integer orderDetailId) {
        return ResponseEntity.ok(orderDetailService.get(orderDetailId));
    }

    @PostMapping
    public ResponseEntity<Integer> createOrderDetail(
            @RequestBody @Valid final OrderDetailDTO orderDetailDTO) {
        final Integer createdOrderDetailId = orderDetailService.create(orderDetailDTO);
        return new ResponseEntity<>(createdOrderDetailId, HttpStatus.CREATED);
    }

    @PutMapping("/{orderDetailId}")
    public ResponseEntity<Integer> updateOrderDetail(
            @PathVariable(name = "orderDetailId") final Integer orderDetailId,
            @RequestBody @Valid final OrderDetailDTO orderDetailDTO) {
        orderDetailService.update(orderDetailId, orderDetailDTO);
        return ResponseEntity.ok(orderDetailId);
    }

    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<Void> deleteOrderDetail(
            @PathVariable(name = "orderDetailId") final Integer orderDetailId) {
        orderDetailService.delete(orderDetailId);
        return ResponseEntity.noContent().build();
    }

}
