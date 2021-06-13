package org.esk.diobeerstockapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class OrderItem {
    @JsonIgnore
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Double discount;
    private Integer amount;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Order order, Beer beer, Double discount, Integer amount, Double price) {
        super();
        this.id.setOrder(order);
        this.id.setBeer(beer);
        this.discount = discount;
        this.amount = amount;
        this.price = price;
    }

    public double getSubTotal() {
        return (price - discount) * amount;
    }

    @JsonIgnore
    public Order getOrder() {
        return this.id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public Beer getBeer() {
        return this.id.getBeer();
    }

    public void setBeer(Beer beer) {
        id.setBeer(beer);
    }

    public OrderItemPK getId() {
        return id;
    }

    public void setId(OrderItemPK id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
