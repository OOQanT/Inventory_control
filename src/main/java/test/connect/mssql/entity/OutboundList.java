package test.connect.mssql.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboundList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int totalPrice;

    @CreationTimestamp
    private LocalDateTime requestTime;

    public OutboundList(String requester, Product product, int quantity) {
        this.requester = requester;
        this.product = product;
        this.quantity = quantity;
    }

    public void setTotalPrice(){
        this.totalPrice = quantity * product.getPrice();
    }

}
