package entities;

import enums.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Integer id;
    private Product product;
    private  Integer quantity;
    private Type type;
    private LocalDateTime localDateTime;

    public Transaction() {
    }

    public Transaction(Integer id, Product product, Integer quantity, Type type, LocalDateTime localDateTime) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.localDateTime = localDateTime;
    }


    public Transaction(Product product, Integer qty, Type type, LocalDateTime now) {
        this.product = product;
        this.quantity = qty;
        this.type = type;
        this.localDateTime = now;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}