package test.connect.mssql.service.outboundlist;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.connect.mssql.dto.outboudlist.AddItemRequest;
import test.connect.mssql.entity.OutboundList;
import test.connect.mssql.entity.Product;
import test.connect.mssql.repository.outboundlist.OutBoundListRepository;
import test.connect.mssql.repository.product.ProductRepository;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OutBoundListService {

    private final OutBoundListRepository outBoundListRepository;
    private final ProductRepository productRepository;

    public OutboundList addItem(AddItemRequest addItemRequest){
        Product product = productRepository.findById(addItemRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));

        if(product.getQuantity() < addItemRequest.getQuantity()){
            throw new IllegalArgumentException("현재 재고보다 많은 수량은 주문할 수 없습니다.");
        }

        OutboundList outboundList = new OutboundList("requester", product, addItemRequest.getQuantity());
        outboundList.setTotalPrice();

        outBoundListRepository.save(outboundList);

        return outboundList;
    }
}
