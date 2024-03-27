package test.connect.mssql.dto.product;

import lombok.Getter;
import lombok.Setter;
import test.connect.mssql.entity.Product;

@Getter
@Setter
public class FindProductResponse {

    private Long id;
    private String productName;
    private String code;
    private int price;
    private int quantity;

    public FindProductResponse() {
    }

    public FindProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.code = product.getCode();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

}
