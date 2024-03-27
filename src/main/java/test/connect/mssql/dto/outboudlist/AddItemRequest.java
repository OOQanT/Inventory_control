package test.connect.mssql.dto.outboudlist;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequest {

    private Long id;

    @Min(value = 10, message = "10개 이상부터 주문이 가능합니다.")
    private int quantity;

    public AddItemRequest() {
    }

    public AddItemRequest(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
