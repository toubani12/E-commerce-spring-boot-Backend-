package io.bootify.my_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;


public class OrderDetailDTO {

    private Integer orderDetailId;

    @NotNull
    private Integer quantity;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pricePerUnit;

    @NotNull
    private Integer order;

    @NotNull
    private Integer product;

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(final Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(final BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(final Integer product) {
        this.product = product;
    }

}
