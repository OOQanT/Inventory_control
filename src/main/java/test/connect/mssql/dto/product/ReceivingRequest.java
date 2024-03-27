package test.connect.mssql.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ReceivingRequest {

    @NotBlank(message = "상품의 이름을 입력해주세요")
    private String productName;

    @Min(value = 10, message = "상품의 수량은 최소 10개 이상이어야 합니다.")
    @NotNull(message = "상품의 개수를 입력해주세요")
    private int quantity;

    @NotNull(message = "상품의 가격을 입력해주세요")
    @Range(min = 1000, max = 1000000)
    private int price;

    @NotBlank(message = "상품코드를 입력해주세요")
    private String code;

    public ReceivingRequest() {
    }

    public ReceivingRequest(String productName, int quantity, int price, String code) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.code = code;
    }
}
