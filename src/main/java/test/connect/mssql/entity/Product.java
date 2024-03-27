package test.connect.mssql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import test.connect.mssql.dto.product.ReceivingRequest;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String productName;

    @Column(length = 30, nullable = false)
    private String code;


    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp
    private LocalDateTime receiveDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    public Product(String productName, String code, int price, int quantity) {
        this.productName = productName;
        this.code = code;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(ReceivingRequest receivingRequest) {
        this.productName = receivingRequest.getProductName();
        this.code = receivingRequest.getCode();
        this.quantity = receivingRequest.getQuantity();
        this.price = receivingRequest.getPrice();
    }
}
